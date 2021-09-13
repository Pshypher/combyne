package com.combyne.android.tvshowmanager.allshows.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.allshows.domain.TvShow
import java.lang.IllegalArgumentException

class AllShowsViewModelFactory(private val fetchShows: QueryUseCase<Resource<List<TvShow>>>) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllShowsViewModel::class.java)) {
            AllShowsViewModel(fetchShows) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class.")
        }
    }
}