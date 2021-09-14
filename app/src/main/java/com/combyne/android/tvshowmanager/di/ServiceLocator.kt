package com.combyne.android.tvshowmanager.di

import com.apollographql.apollo.ApolloClient
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.interactors.AddMovie
import com.combyne.android.tvshowmanager.addmovie.interactors.ValidateEntry
import com.combyne.android.tvshowmanager.movies.data.datasource.MoviesGraphQLDataSource
import com.combyne.android.tvshowmanager.movies.data.datasource.MoviesRemoteDataSource
import com.combyne.android.tvshowmanager.movies.data.repository.MoviesAbstractRepository
import com.combyne.android.tvshowmanager.movies.data.repository.MoviesRepository
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.movies.interactor.FetchAllMovies

typealias MovieMetaData = com.combyne.android.tvshowmanager.addmovie.domain.Movie

object ServiceLocator {

    private const val BASE_URL = "https://tv-show-manager.combyne.com/graphql"

    private val client by lazy {
        provideApolloClient()
    }

    fun provideAddShowUseCase(): CreateUseCase<MovieMetaData> =
        AddMovie()

    fun provideValidateEntryUseCase(): ValidateEntryUseCase<MovieMetaData> =
        ValidateEntry()

    fun provideDataSource(): MoviesRemoteDataSource =
        MoviesGraphQLDataSource.create(client)

    fun provideRepository(): MoviesAbstractRepository =
        MoviesRepository.create(provideDataSource())

    fun provideQueryMoviesUseCase(): QueryUseCase<Resource<List<Movie>>> =
        FetchAllMovies.create(provideRepository())

    fun provideApolloClient() =
        ApolloClient.builder()
            .serverUrl(BASE_URL)
            .build()


}