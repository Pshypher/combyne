package com.combyne.android.tvshowmanager.movies.controller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.combyne.android.tvshowmanager.MainCoroutineRule
import com.combyne.android.tvshowmanager.getOrAwaitValue
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.movies.interactors.FakeTestFetchMovies
import com.combyne.android.tvshowmanager.network.Resource.Status.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MoviesViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    private var movies: List<Movie>? = null

    @Before
    fun setup() {
        movies = listOf(
            Movie("Movie1", "19 September 2019", "1"),
            Movie("Movie2", "20 September 2020", "4"),
            Movie("Movie3", "21 September 2018", "2"),
            Movie("Movie4", "22 September 2022", "2"),
            Movie("Movie5", "23 September 2019", "3"),
            Movie("Movie6", "24 September 2023", "1"),
            Movie("Movie7", "25 September 2019", "1"),
            Movie("Movie8", "26 September 2020", "5"),
            Movie("Movie9", "27 September 2019", "1"),
            Movie("Movie10", "28 September 2019", "1"),
            Movie("Movie11", "29 September 2023", "2"),
            Movie("Movie12", "30 September 2021", "5"),
            Movie("Movie13", "1 October 2020", "4"),
            Movie("Movie14", "2 October 2022", "5"),
            Movie("Movie15", "3 October 2018", "5")
        )
    }

    @Test
    fun allMoviesViewModel_fetchMovies_returnsPagedListOfMovieItems() {
        // Given - A useCase that has successfully retrieved a list of movie items
        //         and a new MoviesViewModel instance
        val pageSize = 5
        val useCase = FakeTestFetchMovies.create(movies, pageSize)
        val viewModel = MoviesViewModel(useCase)

        // When - An attempt is made to retrieve a list of movies
        mainCoroutineRule.pauseDispatcher()
        viewModel.getAllShows()

        // Then - A success is returned and the returned size of the
        // list of items equals the page size
        mainCoroutineRule.resumeDispatcher()
        val result = viewModel.result.getOrAwaitValue()
        val list = result.data
        assertThat(result.status, instanceOf(SUCCESS::class.java))
        assertEquals(pageSize, list?.size)
    }

    @Test
    fun allMoviesViewModel_errorRetrievingMovies_errorStatusReturned() {
        // Given - A useCase that fails to retrieve a list of movie items
        //         and a new MoviesViewModel instance
        val pageSize = 5
        val useCase = FakeTestFetchMovies.create(null, pageSize)
        val viewModel = MoviesViewModel(useCase)

        // When - An attempt is made to retrieve a list of movies
        viewModel.getAllShows()

        // Then - An error status is returned
        // and the size of the list equals to 0
        val result = viewModel.result.getOrAwaitValue()
        val list = result.data
        assertThat(result.status, instanceOf(ERROR::class.java))
        assertEquals(0, list?.size ?: 0)
    }

    @Test
    fun allMoviesViewModel_addMovie_addMovieEventTriggered() {
        // Given - A new ViewModel instance
        val viewModel = MoviesViewModel(FakeTestFetchMovies.create(movies))

        // When - A movie is to be added
        viewModel.addShow()

        // Then - An add movie event is triggered
        val value = viewModel.addShow.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), `is`(true))
    }

}