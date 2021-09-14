package com.combyne.android.tvshowmanager.movies.data.datasource

import com.apollographql.apollo.api.Response
import com.combyne.android.tvshowmanager.ShowMoviesQuery

interface MoviesRemoteDataSource {

    suspend fun queryAllMovies(cursor: String?): Response<ShowMoviesQuery.Data>
}