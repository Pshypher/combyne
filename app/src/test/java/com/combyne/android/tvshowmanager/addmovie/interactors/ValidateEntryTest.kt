package com.combyne.android.tvshowmanager.addmovie.interactors

import com.combyne.android.tvshowmanager.addmovie.domain.Movie
import org.junit.Assert.*
import org.junit.Test

class ValidateEntryTest {

    // If there is at least one null field within the Movie object,
    // then the entry appears to be invalid
    @Test
    fun validateMovieEntry_nullFields_returnsFalse() {
        // Given - A movie with a title, no releaseDate and no season
        val movie = Movie("What if?", null, null)

        // Then - it should not be a valid entry
        assertFalse(ValidateEntry().isValid(movie))
    }

    // If there is at least one empty string within the Movie object
    // then the entry would also be invalid
    @Test
    fun validateMovieEntry_emptyFields_returnsFalse() {
        // Given - A movie with no title, a releaseDate and no season
        val movie = Movie("", "4 August 2018", "")

        // Then - it should be marked as an invalid entry
        assertFalse(ValidateEntry().isValid(movie))
    }

    // If there is at least one field comprised of only white spaces within
    // the Movie object the entry is also not valid
    @Test
    fun validateMovieEntry_fieldsWithWhiteSpaces_returnsFalse() {
        // Given - A movie object whose field has only white spaces
        val movie = Movie("Carnival Row", "          ", "1")

        // Then - it should indicate that the entry is invalid
        assertFalse(ValidateEntry().isValid(movie))
    }

    // If all fields within the Movie object contains alphanumeric character(s)
    // then the entry is a valid one
    @Test
    fun validateMovieEntry_allFieldsContainsAlphanumericChars_returnsTrue() {
        // Given - A movie object whose field has only white spaces
        val movie = Movie("Carnival Row", "22 October 2019", "1")

        // Then - it should indicate that the entry is invalid
        assertTrue(ValidateEntry().isValid(movie))
    }
}