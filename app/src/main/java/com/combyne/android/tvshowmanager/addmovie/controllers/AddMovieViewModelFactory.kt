package com.combyne.android.tvshowmanager.addmovie.controllers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import java.lang.IllegalArgumentException

class AddMovieViewModelFactory(
    private val application: Application,
    private val addMovie: CreateUseCase<Movie>,
    private val validate: ValidateEntryUseCase<Movie>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddMovieViewModel::class.java)) {
            AddMovieViewModel(application, addMovie, validate) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}