package com.combyne.android.tvshowmanager.addshows.controllers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addshows.domain.Show
import java.lang.IllegalArgumentException

class AddShowViewModelFactory(
    private val application: Application,
    private val addShow: CreateUseCase<Show>,
    private val validate: ValidateEntryUseCase<Show>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddShowViewModel::class.java)) {
            AddShowViewModel(application, addShow, validate) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}