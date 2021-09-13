package com.combyne.android.tvshowmanager.allshows.interactor

import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.allshows.data.repository.AllShowsAbstractRepository
import com.combyne.android.tvshowmanager.allshows.domain.TvShow

class FetchAllShows(private val repository: AllShowsAbstractRepository) :
    QueryUseCase<Resource<List<TvShow>>> {

    override suspend fun query(): Resource<List<TvShow>>? {
        TODO("Not yet implemented")
    }
}