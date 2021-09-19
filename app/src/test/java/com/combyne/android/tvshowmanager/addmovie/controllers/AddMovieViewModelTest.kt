package com.combyne.android.tvshowmanager.addmovie.controllers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.addmovie.interactors.FakeTestAddMovie
import com.combyne.android.tvshowmanager.addmovie.interactors.FakeTestValidateEntry
import com.combyne.android.tvshowmanager.getOrAwaitValue
import com.combyne.android.tvshowmanager.network.Resource.Status.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AddMovieViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun addMovie_dismissBottomDialogEvent() {
        // Given a movie and a new instance of AddMovieViewModel
        val movie = Movie("Mr. Robot", "27 September 2019", "3.0")

        val viewModel = AddMovieViewModel(
            ApplicationProvider.getApplicationContext(),
            FakeTestAddMovie.create(movie),
            FakeTestValidateEntry.create(true)
        )

        // When adding the movie
        viewModel.addMovie(movie)

        // Then - the status is changed
        val status = viewModel.status.getOrAwaitValue()
        assertThat(status.getContentIfNotHandled(), `is`(LOADING))

        // Would handle this properly when we reach the section on testing coroutines
        assertThat(status.getContentIfNotHandled(), anyOf(`is`(ERROR), `is`(SUCCESS)))

        // The dismiss bottom dialog event is triggered
        val event = viewModel.dismissBottomDialogEvent.getOrAwaitValue()
        assertThat(event.getContentIfNotHandled(), `is`(true))
    }

    @Test
    fun addMovie_withNullOrBlankField_triggerMissingFieldEntryEvent() {
        // Given a new instance of AddMovieViewModel
        val movie = Movie("Altered Carbon", "     ", "2")
        val viewModel = AddMovieViewModel(
            ApplicationProvider.getApplicationContext(),
            FakeTestAddMovie.create(movie),
            FakeTestValidateEntry.create(false)
        )

        // When adding a new movie
        viewModel.addMovie(movie)

        // Then - the missing field entry event is triggered
        val event = viewModel.missingEntryFieldEvent.getOrAwaitValue()
        assertThat(event.getContentIfNotHandled(), instanceOf(String::class.java))
    }
}