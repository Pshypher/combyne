package com.combyne.android.tvshowmanager.application

import android.app.Application
import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.QueryUseCase
import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.di.MovieMetaData
import com.combyne.android.tvshowmanager.di.ServiceLocator
import com.combyne.android.tvshowmanager.movies.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource

class TVShowApplication : Application() {

    val validator: ValidateEntryUseCase<MovieMetaData>
        get() = ServiceLocator.provideValidateEntryUseCase()

    val query: QueryUseCase<Resource<List<Movie>>>
        get() = ServiceLocator.provideQueryMoviesUseCase()

    val mutator: CreateUseCase<MovieMetaData, Resource<AddMovieMutation.Movie?>>
        get() = ServiceLocator.provideAddMovieUseCase()
}