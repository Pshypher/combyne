package com.combyne.android.tvshowmanager.addshows.controllers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.Event
import com.combyne.android.tvshowmanager.R
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addshows.domain.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AddShowViewModel(
    application: Application,
    private val addShow: CreateUseCase<Show>,
    private val validate: ValidateEntryUseCase<Show>
) : AndroidViewModel(application) {

    private val _missingEntryFieldEvent = MutableLiveData<Event<String>>()
    val missingEntryFieldEvent: LiveData<Event<String>>
        get() = _missingEntryFieldEvent

    private val _dismissBottomDialogEvent = MutableLiveData<Event<Boolean>>()
    val dismissBottomDialogEvent: LiveData<Event<Boolean>>
        get() = _dismissBottomDialogEvent

    fun addShow(tvShow: Show) {
        if (!validate.isValid(tvShow)) {
            _missingEntryFieldEvent.value =
                Event(getApplication<Application>().resources.getString(R.string.missing_entry_fields))
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    addShow.create(tvShow)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _dismissBottomDialogEvent.value = Event(true)
                }
            }
        }
    }
}