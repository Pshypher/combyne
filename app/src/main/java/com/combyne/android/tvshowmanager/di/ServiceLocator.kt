package com.combyne.android.tvshowmanager.di

import com.apollographql.apollo.ApolloClient
import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.data.datasource.AddMovieGraphQLDataSource
import com.combyne.android.tvshowmanager.addmovie.data.datasource.AddMovieRemoteDataSource
import com.combyne.android.tvshowmanager.addmovie.data.repository.AddMovieRepository
import com.combyne.android.tvshowmanager.addmovie.interactors.AddMovie
import com.combyne.android.tvshowmanager.addmovie.interactors.ValidateEntry
import com.combyne.android.tvshowmanager.movies.data.datasource.MoviesGraphQLDataSource
import com.combyne.android.tvshowmanager.movies.data.datasource.MoviesRemoteDataSource
import com.combyne.android.tvshowmanager.movies.data.repository.MoviesAbstractRepository
import com.combyne.android.tvshowmanager.movies.data.repository.MoviesRepository
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.movies.interactor.FetchAllMovies
import com.combyne.android.tvshowmanager.network.Resource
import okhttp3.OkHttpClient

typealias MovieMetaData = com.combyne.android.tvshowmanager.addmovie.domain.Movie

object ServiceLocator {

    private const val BASE_URL = "https://tv-show-manager.combyne.com/graphql"
    private const val CLIENT_KEY =
        "yiCk1DW6WHWG58wjj3C4pB/WyhpokCeDeSQEXA5HaicgGh4pTUd+3/rMOR5xu1Yi"
    private const val APPLICATION_ID =
        "AaQjHwTIQtkCOhtjJaN/nDtMdiftbzMWW5N8uRZ+DNX9LI8AOziS10eHuryBEcCI"

    private val client by lazy {
        provideApolloClient()
    }

    fun provideAddMovieDataSource(): AddMovieRemoteDataSource =
        AddMovieGraphQLDataSource.create(client)

    fun provideAddMovieRepository(): AddMovieRepository =
        AddMovieRepository.create(provideAddMovieDataSource())

    fun provideAddMovieUseCase(): CreateUseCase<MovieMetaData, Resource<AddMovieMutation.Movie?>> =
        AddMovie.create(provideAddMovieRepository())

    fun provideValidateEntryUseCase(): ValidateEntryUseCase<MovieMetaData> =
        ValidateEntry()

    fun provideMoviesDataSource(): MoviesRemoteDataSource =
        MoviesGraphQLDataSource.create(client)

    fun provideMoviesRepository(): MoviesAbstractRepository =
        MoviesRepository.create(provideMoviesDataSource())

    fun provideQueryMoviesUseCase(): QueryUseCase<Resource<List<Movie>>> =
        FetchAllMovies.create(provideMoviesRepository())

    fun provideApolloClient() =
        ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("X-Parse-Client-Key", CLIENT_KEY)
                        .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                        .build()

                    chain.proceed(request)
                }.build()
            )
            .build()
}