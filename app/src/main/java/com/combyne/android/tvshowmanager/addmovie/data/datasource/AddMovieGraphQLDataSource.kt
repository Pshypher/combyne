package com.combyne.android.tvshowmanager.addmovie.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.combyne.android.tvshowmanager.AddMovieMutation

class AddMovieGraphQLDataSource private constructor(): AddMovieRemoteDataSource {

    private lateinit var apolloClient: ApolloClient

    override suspend fun addMovie(): Response<AddMovieMutation.Data> {
        TODO("Not yet implemented")
    }

    companion object {

        fun create(client: ApolloClient): AddMovieGraphQLDataSource {
            return AddMovieGraphQLDataSource().apply {
                apolloClient = client
            }
        }
    }
}