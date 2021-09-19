package com.combyne.android.tvshowmanager.addmovie.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
import com.combyne.android.tvshowmanager.*
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.network.Resource.Status.*
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

    private val _status = MutableLiveData<Event<Resource.Status>>()
    val status: LiveData<Event<Resource.Status>>
        get() = _status

    fun addMovie(movie: Movie) {
        if (!isValid(movie)) return

        _status.value = Event(LOADING)
        viewModelScope.launch {
            try {
                val result = addMovie.post(movie)
                when (result.status) {
                    SUCCESS -> {
                        _status.value = Event(SUCCESS)
                    }
                    ERROR -> {
                        _status.value = Event(ERROR)
                    }
                    else -> return@launch
                }
            } catch (e: ApolloException) {
                e.printStackTrace()
            } finally {
                dismiss()
            }
        }
    }

    private fun isValid(movie: Movie): Boolean {
        return if (!validate.isValid(movie)) {
            _missingEntryFieldEvent.value =
                Event(getApplication<Application>().resources.getString(R.string.missing_entry_fields))
            false
        } else {
            true
        }
    }

    private fun dismiss() {
        _dismissBottomDialogEvent.postValue(Event(true))
    }
}