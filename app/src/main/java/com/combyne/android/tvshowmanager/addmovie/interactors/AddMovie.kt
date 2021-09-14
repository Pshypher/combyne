package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.addmovie.data.repository.AddMovieAbstractRepository
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class AddMovie private constructor() : CreateUseCase<Movie, Resource<AddMovieMutation.Movie?>> {

    private lateinit var repository: AddMovieAbstractRepository

    override suspend fun post(t: Movie): Resource<AddMovieMutation.Movie?> {
        return repository.addMovie(t)
    }

    companion object {

        fun create(repo: AddMovieAbstractRepository): AddMovie {
            return AddMovie().apply {
                repository = repo
            }
        }
    }
}