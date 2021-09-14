package com.combyne.android.tvshowmanager.movies.data.repository

import com.apollographql.apollo.api.Response
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.ShowMoviesQuery
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.movies.data.datasource.MoviesRemoteDataSource

class MoviesRepository private constructor() : MoviesAbstractRepository {

    private lateinit var dataSource: MoviesRemoteDataSource

    override suspend fun fetchAllMovies(cursor: String?): Triple<Resource<List<Movie>>, String?, Boolean>? {
        val response = dataSource.queryAllMovies(cursor)
        return parseResponse(response)
    }

    private fun parseResponse(response: Response<ShowMoviesQuery.Data>): Triple<Resource<List<Movie>>, String?, Boolean>? {
        return if (response.hasErrors()) {
            Triple(Resource.error(), null, false)
        } else {
            response.data?.movies?.let {
                Triple(Resource.success(
                    it.edges?.map { edge ->
                        Movie().apply {
                            title = edge?.node?.title
                            releaseDate = edge?.node?.releaseDate.toString()
                            seasons = edge?.node?.seasons.toString()
                        }
                    } ?: listOf()), it.pageInfo.endCursor, true)
            }
        }
    }

    companion object {

        fun create(source: MoviesRemoteDataSource): MoviesRepository {
            return MoviesRepository().apply {
                dataSource = source
            }
        }
    }
}