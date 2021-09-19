package com.combyne.android.tvshowmanager.movies.controller

import androidx.lifecycle.*
import com.apollographql.apollo.exception.ApolloException
import com.combyne.android.tvshowmanager.Event
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.network.Resource.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class MoviesViewModel(private val fetchMovies: QueryUseCase<Resource<List<Movie>>>) : ViewModel() {

    private val _addShow = MutableLiveData<Event<Boolean>>()
    val addShow: LiveData<Event<Boolean>>
        get() = _addShow

    val channel = Channel<Unit>(Channel.CONFLATED)

    private val _endOfList = MutableLiveData<Event<Boolean>>()
    val endOfList: LiveData<Event<Boolean>>
        get() = _endOfList

    val result = MediatorLiveData<Resource<List<Movie>>>()
    private val shows = MutableLiveData<Resource<List<Movie>>>()
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

        // offer a first item to do the initial load else the list will stay empty forever
        channel.trySend(Unit).isSuccess

        getAllShows()
    }

    fun getAllShows() {
        var cursor: String? = null
        var hasMore = true
        status.value = Status.LOADING
        viewModelScope.launch {
            for (item in channel) {
                try {
                    fetchMovies.query(cursor)?.also { (result, endCursor, hasNextPage) ->
                        shows.value = result
                        cursor = endCursor
                        hasMore = hasNextPage
                    }
                    status.value = Status.SUCCESS
                } catch (e: ApolloException) {
                    e.printStackTrace()
                    status.value = Status.ERROR
                }

                if (!hasMore) break
                _endOfList.value = Event(true)
                channel.close()
            }
        }
    }

    fun addShow() {
        _addShow.value = Event(true)
    }
}