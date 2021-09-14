package com.combyne.android.tvshowmanager.addmovie.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.type.CreateMovieFieldsInput
import com.combyne.android.tvshowmanager.type.CreateMovieInput

class AddMovieGraphQLDataSource private constructor() : AddMovieRemoteDataSource {

    private lateinit var apolloClient: ApolloClient

    override suspend fun addMovie(movie: Movie): Response<AddMovieMutation.Data> {
        val fields = CreateMovieFieldsInput(
            title = movie.title!!,
            releaseDate = Input.fromNullable(movie.releaseDate),
            seasons = Input.fromNullable(movie.season?.toDouble())
        )
        val input = CreateMovieInput(Input.fromNullable(fields))
        return apolloClient.mutate(AddMovieMutation(input)).await()
    }

    companion object {

        fun create(client: ApolloClient): AddMovieGraphQLDataSource {
            return AddMovieGraphQLDataSource().apply {
                apolloClient = client
            }
        }
    }
}