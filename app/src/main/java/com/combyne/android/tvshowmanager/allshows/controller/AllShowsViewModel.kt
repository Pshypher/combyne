package com.combyne.android.tvshowmanager.allshows.controller

import androidx.lifecycle.*
import com.combyne.android.tvshowmanager.Event
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.Resource.Status
import com.combyne.android.tvshowmanager.allshows.domain.TvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllShowsViewModel(private val fetchShows: QueryUseCase<Resource<List<TvShow>>>) : ViewModel() {

    private val _addShow = MutableLiveData<Event<Boolean>>()
    val addShow: LiveData<Event<Boolean>>
        get() = _addShow

    val result = MediatorLiveData<Resource<List<TvShow>>>()
    private val shows = MutableLiveData<Resource<List<TvShow>>>()
    private val status = MutableLiveData<Status>()

    init {
        result.addSource(shows) { res ->
            result.value = res.data?.let {
                Resource.success(it)
            } ?: Resource.error()
        }
        // when the status changes update the result with the corresponding status
        result.addSource(status) { s ->
            s?.let {
                when (it) {
                    Status.LOADING -> result.value = Resource.loading(result.value?.data)
                    Status.ERROR -> result.value = Resource.error(result.value?.data)
                    Status.SUCCESS -> result.value?.data?.also {
                        result.value = Resource.success(it)
                    }
                }
            }
        }
    }

    fun getAllShows() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                status.value = Status.LOADING
                fetchShows.query()?.also {
                    shows.value = it
                }

            } catch (e: Exception) {
                e.printStackTrace()

            }

        }
    }

    fun addShow() {
        _addShow.value = Event(true)
    }
}