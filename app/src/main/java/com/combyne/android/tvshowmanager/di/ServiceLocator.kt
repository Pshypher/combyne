package com.combyne.android.tvshowmanager.di

import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.Resource
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addshows.domain.Show
import com.combyne.android.tvshowmanager.addshows.interactors.AddTvShow
import com.combyne.android.tvshowmanager.addshows.interactors.ValidateEntry
import com.combyne.android.tvshowmanager.allshows.data.datasource.AllShowsGraphQLDataSource
import com.combyne.android.tvshowmanager.allshows.data.datasource.AllShowsRemoteDataSource
import com.combyne.android.tvshowmanager.allshows.data.repository.AllShowsAbstractRepository
import com.combyne.android.tvshowmanager.allshows.data.repository.AllShowsRepository
import com.combyne.android.tvshowmanager.allshows.domain.TvShow
import com.combyne.android.tvshowmanager.allshows.interactor.FetchAllShows

object ServiceLocator {

    fun provideAddShowUseCase(): CreateUseCase<Show> =
        AddTvShow()

    fun provideValidateEntryUseCase(): ValidateEntryUseCase<Show> =
        ValidateEntry()

    fun provideDataSource(): AllShowsRemoteDataSource =
        AllShowsGraphQLDataSource()

    fun provideRepository(): AllShowsAbstractRepository =
        AllShowsRepository(provideDataSource())

    fun provideQueryShowsUseCase(): QueryUseCase<Resource<List<TvShow>>> =
        FetchAllShows(provideRepository())
}