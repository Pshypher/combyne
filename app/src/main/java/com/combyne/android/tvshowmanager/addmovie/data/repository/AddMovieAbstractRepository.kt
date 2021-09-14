package com.combyne.android.tvshowmanager.addmovie.data.repository

import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

interface AddMovieAbstractRepository {

    suspend fun addMovie(movie: Movie): Resource<AddMovieMutation.Movie?>
}