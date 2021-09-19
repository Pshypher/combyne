package com.combyne.android.tvshowmanager.movies.interactors

import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class FakeTestFetchMovies private constructor(
    private var data: List<Movie>?,
    private var pageSize: Int = 10
) : QueryUseCase<Resource<List<Movie>>> {

    override suspend fun query(cursor: String?): Triple<Resource<List<Movie>>, String?, Boolean> {
        return data?.let {
            val startCursor = (cursor?.toInt() ?: 0) + 1
            val hasMore = it.isEmpty() || it.size < (startCursor + pageSize - 1)
            val endCursor =
                if (it.size < (startCursor + pageSize - 1)) it.size else startCursor + pageSize - 1
            Triple(Resource.success(it.slice(startCursor..endCursor)), endCursor.toString(), hasMore)

        } ?: Triple(Resource.error(data), "0", false)
    }

    companion object {

        fun create(data: List<Movie>, pageSize: Int): FakeTestFetchMovies {
            return FakeTestFetchMovies(data, pageSize)
        }
    }

}