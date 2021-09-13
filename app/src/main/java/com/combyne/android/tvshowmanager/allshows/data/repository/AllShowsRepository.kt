package com.combyne.android.tvshowmanager.allshows.data.repository

import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.allshows.data.Show
import com.combyne.android.tvshowmanager.allshows.data.datasource.AllShowsRemoteDataSource

class AllShowsRepository(private val dataSource: AllShowsRemoteDataSource) : AllShowsAbstractRepository {

    override suspend fun fetchAllShows(): Resource<Show> {
        TODO("Not yet implemented")
    }
}