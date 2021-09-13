package com.combyne.android.tvshowmanager.allshows.data.datasource

interface AllShowsRemoteDataSource {

    suspend fun queryAllShows()
}