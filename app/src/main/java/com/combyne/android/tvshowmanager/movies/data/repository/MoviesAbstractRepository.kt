package com.combyne.android.tvshowmanager.movies.data.repository

import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.movies.domain.Movie

interface MoviesAbstractRepository {

    suspend fun fetchAllMovies(cursor: String?): Triple<Resource<List<Movie>>, String?, Boolean>?
}