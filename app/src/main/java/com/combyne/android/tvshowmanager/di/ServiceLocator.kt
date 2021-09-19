package com.combyne.android.tvshowmanager.di

import androidx.annotation.VisibleForTesting
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
import com.combyne.android.tvshowmanager.movies.interactors.FetchAllMovies
import com.combyne.android.tvshowmanager.network.Resource
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

typealias MovieMetaData = com.combyne.android.tvshowmanager.addmovie.domain.Movie

object ServiceLocator {

    private const val BASE_URL = "https://tv-show-manager.combyne.com/graphql"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 15L

    private const val CLIENT_KEY =
        "yiCk1DW6WHWG58wjj3C4pB/WyhpokCeDeSQEXA5HaicgGh4pTUd+3/rMOR5xu1Yi"
    private const val APPLICATION_ID =
        "AaQjHwTIQtkCOhtjJaN/nDtMdiftbzMWW5N8uRZ+DNX9LI8AOziS10eHuryBEcCI"

    private val lock = Any()

    private val client by lazy {
        createApolloClient()
    }

    var query: QueryUseCase<Resource<List<Movie>>>? = null
        @VisibleForTesting set

    var mutator: CreateUseCase<MovieMetaData, Resource<AddMovieMutation.Movie?>>? = null
        @VisibleForTesting set

    var validator: ValidateEntryUseCase<MovieMetaData>? = null
        @VisibleForTesting set

    private fun createAddMovieDataSource(): AddMovieRemoteDataSource =
        AddMovieGraphQLDataSource.create(client)

    private fun createAddMovieRepository(): AddMovieRepository =
        AddMovieRepository.create(createAddMovieDataSource())

    private fun createAddMovieUseCase(): CreateUseCase<MovieMetaData, Resource<AddMovieMutation.Movie?>> {
        val useCase = AddMovie.create(createAddMovieRepository())
        mutator = useCase
        return useCase
    }

    fun provideAddMovieUseCase(): CreateUseCase<MovieMetaData, Resource<AddMovieMutation.Movie?>> =
        mutator ?: createAddMovieUseCase()

    private fun createValidateEntryUseCase(): ValidateEntryUseCase<MovieMetaData> {
        val useCase = ValidateEntry()
        validator = useCase
        return useCase
    }

    fun provideValidateEntryUseCase(): ValidateEntryUseCase<MovieMetaData> =
        validator ?: createValidateEntryUseCase()

    private fun createMoviesDataSource(): MoviesRemoteDataSource =
        MoviesGraphQLDataSource.create(client)

    private fun createMoviesRepository(): MoviesAbstractRepository =
        MoviesRepository.create(createMoviesDataSource())

    private fun createQueryMovieUseCase(): QueryUseCase<Resource<List<Movie>>> {
        val useCase = FetchAllMovies.create(createMoviesRepository())
        query = useCase
        return useCase
    }

    fun provideQueryMoviesUseCase(): QueryUseCase<Resource<List<Movie>>> =
        query ?: createQueryMovieUseCase()

    private fun createApolloClient(): ApolloClient = synchronized(lock) {
        ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder()
                            .addHeader("X-Parse-Client-Key", CLIENT_KEY)
                            .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                            .build()

                        chain.proceed(request)
                    }.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .build()
            ).build()
    }

    @VisibleForTesting
    fun reset() {
        query = null
        mutator = null
        validator = null
    }
}