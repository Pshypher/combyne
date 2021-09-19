package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.AddMovieMutation
import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import com.combyne.android.tvshowmanager.network.Resource
import com.combyne.android.tvshowmanager.utils.DateParser

class FakeTestAddMovie private constructor(private var movie: Movie) :
    CreateUseCase<Movie, Resource<AddMovieMutation.Movie?>> {

    override suspend fun post(t: Movie): Resource<AddMovieMutation.Movie?> {
        return Resource.success(
            AddMovieMutation.Movie(
                title = movie.title ?: DEFAULT_TITLE,
                releaseDate = movie.releaseDate ?: DEFAULT_RELEASE_DATE,
                seasons = movie.season?.toDouble() ?: DEFAULT_SEASON
            )
        )
    }

    companion object {

        private const val DEFAULT_TITLE = "title"
        private const val DEFAULT_RELEASE_DATE = "1 January 1970"
        private const val DEFAULT_SEASON = 0.0

        private const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        private const val DATE_PATTERN = "dd MMMM yyyy"

        fun create(movie: Movie): FakeTestAddMovie {
            return FakeTestAddMovie(movie).apply {
                movie.releaseDate =
                    DateParser.parse(DATE_PATTERN, DATE_TIME_PATTERN, movie.releaseDate)
            }
        }
    }
}