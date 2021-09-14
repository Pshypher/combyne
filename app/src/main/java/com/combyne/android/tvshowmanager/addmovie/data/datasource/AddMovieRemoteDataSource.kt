package com.combyne.android.tvshowmanager.addmovie.data.datasource

import com.apollographql.apollo.api.Response
import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.addmovie.domain.Movie


interface AddMovieRemoteDataSource {

    suspend fun addMovie(movie: Movie): Response<AddMovieMutation.Data>
}