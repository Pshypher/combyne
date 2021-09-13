package com.combyne.android.tvshowmanager.addshows.interactors

import com.combyne.android.tvshowmanager.ValidateEntryUseCase
import com.combyne.android.tvshowmanager.addshows.domain.Show

class ValidateEntry : ValidateEntryUseCase<Show> {

    override fun isValid(t: Show): Boolean {
        return with(t) {
            !date.isNullOrEmpty() && !title.isNullOrEmpty()
                    && !season.isNullOrEmpty()
        }
    }
}