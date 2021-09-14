package com.combyne.android.tvshowmanager.addmovie.data.repository

import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.addmovie.data.datasource.AddMovieRemoteDataSource
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class AddMovieRepository private constructor() : AddMovieAbstractRepository {

    private lateinit var dataSource: AddMovieRemoteDataSource

    override suspend fun addMovie(movie: Movie): Resource<AddMovieMutation.Movie?> {
        val response = dataSource.addMovie(movie)
        return if (response.hasErrors() || response.data == null) {
            Resource.error()
        } else {
            Resource.success(response.data?.createMovie?.movie)
        }
    }

    companion object {

        fun create(source: AddMovieRemoteDataSource): AddMovieRepository {
            return AddMovieRepository().apply {
                dataSource = source
            }
        }
    }
}