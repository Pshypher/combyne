package com.combyne.android.tvshowmanager.movies.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class MoviesViewModelFactory(private val fetchShows: QueryUseCase<Resource<List<Movie>>>) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            MoviesViewModel(fetchShows) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class.")
        }
    }
}