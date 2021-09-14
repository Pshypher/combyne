package com.combyne.android.tvshowmanager.addmovie.data.repository

import com.combyne.android.tvshowmanager.network.Resource

interface AddMovieAbstractRepository {

    suspend fun addMovie(): Resource<String>
}