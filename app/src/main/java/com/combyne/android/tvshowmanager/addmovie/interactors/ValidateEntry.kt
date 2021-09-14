package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie

class ValidateEntry : ValidateEntryUseCase<Movie> {

    override fun isValid(t: Movie): Boolean {
        return with(t) {
            !releaseDate.isNullOrEmpty() && !title.isNullOrEmpty()
                    && !season.isNullOrEmpty()
        }
    }
}