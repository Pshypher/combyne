package com.combyne.android.tvshowmanager.addmovie.data.repository

import com.combyne.android.tvshowmanager.addmovie.data.datasource.AddMovieRemoteDataSource
import com.combyne.android.tvshowmanager.network.Resource

class AddMovieRepository private constructor() : AddMovieAbstractRepository {

    private lateinit var dataSource: AddMovieRemoteDataSource

    override suspend fun addMovie(): Resource<String> {
        TODO("Not yet implemented")
    }

    companion object {

        fun create(source: AddMovieRemoteDataSource): AddMovieRepository {
            return AddMovieRepository().apply {
                dataSource = source
            }
        }
    }
}