package com.combyne.android.tvshowmanager.addmovie.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.Event
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AddMovieViewModel(
    application: Application,
    private val addMovie: CreateUseCase<Movie>,
    private val validate: ValidateEntryUseCase<Movie>
) : AndroidViewModel(application) {

    private val _missingEntryFieldEvent = MutableLiveData<Event<String>>()
    val missingEntryFieldEvent: LiveData<Event<String>>
        get() = _missingEntryFieldEvent

    private val _dismissBottomDialogEvent = MutableLiveData<Event<Boolean>>()
    val dismissBottomDialogEvent: LiveData<Event<Boolean>>
        get() = _dismissBottomDialogEvent

    fun addShow(tvMovie: Movie) {
        if (!validate.isValid(tvMovie)) {
            _missingEntryFieldEvent.value =
                Event(getApplication<Application>().resources.getString(R.string.missing_entry_fields))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    addMovie.create(tvMovie)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _dismissBottomDialogEvent.value = Event(true)
                }
            }
        }
    }
}