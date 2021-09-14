package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.CreateUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie

class AddMovie : CreateUseCase<Movie> {

    override suspend fun create(t: Movie) {
        TODO("Not yet implemented")
    }
}