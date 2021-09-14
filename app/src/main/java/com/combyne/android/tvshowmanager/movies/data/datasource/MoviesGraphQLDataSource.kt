package com.combyne.android.tvshowmanager.movies.data.datasource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.combyne.android.tvshowmanager.ShowMoviesQuery

class MoviesGraphQLDataSource private constructor() : MoviesRemoteDataSource {

    private lateinit var apolloClient: ApolloClient

    override suspend fun queryAllMovies(cursor: String?): Response<ShowMoviesQuery.Data> {
        return apolloClient.query(ShowMoviesQuery(Input.fromNullable(cursor))).await()
    }


    companion object {

        fun create(client: ApolloClient): MoviesRemoteDataSource {
            return MoviesGraphQLDataSource().apply {
                apolloClient = client
            }
        }
    }
}