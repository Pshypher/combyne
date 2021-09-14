package com.combyne.android.tvshowmanager.addmovie.data.datasource

import com.apollographql.apollo.api.Response
import com.combyne.android.tvshowmanager.AddMovieMutation


interface AddMovieRemoteDataSource {

    suspend fun addMovie(): Response<AddMovieMutation.Data>
}