package com.combyne.android.tvshowmanager.splash.controllers

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.combyne.android.tvshowmanager.R
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@SmallTest
class SplashFragmentTest {

    @Test
    fun splashScreen_splashFragmentDisplayed() {
        // Given - On the splash screen
        val scenario = launchFragmentInContainer<SplashFragment>(Bundle(), R.style.Theme_AppTheme)

        // Then - the default welcome message is displayed
        onView(withId(R.id.default_message_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.default_message_text_view)).check(matches(withText(R.string.default_message)))
        // the button triggering navigation to the bottom sheet dialog is also displayed
        onView(withId(R.id.add_movie_button)).check(matches(isDisplayed()))
        onView(withId(R.id.add_movie_button)).check(matches(withText(R.string.add_new_movie)))
        // the button that triggers navigation to fragment showing list of movies is also displayed
        onView(withId(R.id.show_movies_button)).check(matches(isDisplayed()))
        onView(withId(R.id.show_movies_button)).check(matches(withText(R.string.show_movies)))

    }

    @Test
    fun splashScreen_hitButtonToRenderSavedMovies_allMoviesDisplayed() {
        // Given - the splash screen fragment
        val scenario = launchFragmentInContainer<SplashFragment>(null, R.style.Theme_AppTheme)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        // When - Show movie button is clicked
        onView(withId(R.id.show_movies_button)).perform(click())

        // Then - Verify that we navigate to the fragment showing all movies
        verify(navController).navigate(R.id.movies_fragment)
    }

    @Test
    fun splashScreen_hitButtonToAddMovie_addMovieBottomSheetDialogDisplayed() {
        // Given - the splash screen fragment
        val scenario = launchFragmentInContainer<SplashFragment>(null, R.style.Theme_AppTheme)
        val navController = mock(NavController::class.java)

        scenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }
        // When - Add movie button is clicked
        onView(withId(R.id.add_movie_button)).perform(click())

        // Then - Verify that we navigate to the dialog holding movie entry fields
        verify(navController).navigate(R.id.add_movies_bottom_dialog_fragment)
    }
}