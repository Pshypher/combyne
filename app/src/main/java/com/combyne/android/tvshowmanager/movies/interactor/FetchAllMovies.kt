package com.combyne.android.tvshowmanager.movies.interactor

import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.movies.data.repository.MoviesAbstractRepository
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class FetchAllMovies private constructor () : QueryUseCase<Resource<List<Movie>>> {

    private lateinit var repository: MoviesAbstractRepository

    override suspend fun query(cursor: String?): Triple<Resource<List<Movie>>, String?, Boolean>? {
        return repository.fetchAllMovies(cursor)
    }

    companion object {
        fun create(repo: MoviesAbstractRepository): FetchAllMovies {
            return FetchAllMovies().apply {
                repository = repo
            }
        }
    }
}