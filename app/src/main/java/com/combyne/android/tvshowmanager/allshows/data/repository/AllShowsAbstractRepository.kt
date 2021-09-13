package com.combyne.android.tvshowmanager.allshows.data.repository

import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.allshows.data.Show

interface AllShowsAbstractRepository {

    suspend fun fetchAllShows(): Resource<Show>
}