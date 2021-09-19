package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addmovie.domain.Movie

class FakeTestValidateEntry private constructor(private var isValid: Boolean) : ValidateEntryUseCase<Movie> {

    override fun isValid(t: Movie): Boolean {
        return isValid
    }

    companion object {

        fun create(isValid: Boolean): FakeTestValidateEntry {
            return FakeTestValidateEntry(isValid)
        }
    }
}