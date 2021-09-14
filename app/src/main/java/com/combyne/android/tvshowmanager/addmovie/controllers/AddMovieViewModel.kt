package com.combyne.android.tvshowmanager.addmovie.controllers

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
import com.combyne.android.tvshowmanager.*
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.network.Resource.Status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMovieViewModel(
    application: Application,
    private val addMovie: CreateUseCase<Movie, Resource<AddMovieMutation.Movie?>>,
    private val validate: ValidateEntryUseCase<Movie>
) : AndroidViewModel(application) {

    private val _missingEntryFieldEvent = MutableLiveData<Event<String>>()
    val missingEntryFieldEvent: LiveData<Event<String>>
        get() = _missingEntryFieldEvent

    private val _dismissBottomDialogEvent = MutableLiveData<Event<Boolean>>()
    val dismissBottomDialogEvent: LiveData<Event<Boolean>>
        get() = _dismissBottomDialogEvent

    fun addShow(movie: Movie) {
        if (!validate.isValid(movie)) {
            _missingEntryFieldEvent.value =
                Event(getApplication<Application>().resources.getString(R.string.missing_entry_fields))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = addMovie.post(movie)
                    when (result.status) {
                        SUCCESS -> Log.i(TAG, "$movie added to remote archive.")
                        ERROR -> Log.e(TAG, "Problem encountered with GraphQL mutate request.")
                        else -> return@launch
                    }

                } catch (e: ApolloException) {
                    e.printStackTrace()
                } finally {
                    _dismissBottomDialogEvent.postValue(Event(true))
                }
            }
        }
    }

    companion object {
        private const val TAG = "AddMovieViewModel"
    }
}