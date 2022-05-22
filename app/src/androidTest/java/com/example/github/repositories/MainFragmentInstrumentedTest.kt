package com.example.github.repositories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.util.BaseTest
import org.hamcrest.Matchers.startsWith
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainFragmentInstrumentedTest : BaseTest() {

    @Test
    fun testTitleShownInRepositoriesList() {
        onView(withId(R.id.repos_list))
            .perform(
                RecyclerViewActions.scrollToPosition<RepositoryAdapter.ViewHolder>(testItemIndex)
            )
        onView(withText(startsWith(testItemTitlePrefix))).check(matches(isDisplayed()))
    }

    @Test
    fun testScreenNavigationWorksFine() {
        val createdByText = context.getString(R.string.label_created_by)
        // click first item
        onView(withId(R.id.repos_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RepositoryAdapter.ViewHolder>(
                    testItemIndex,
                    click()
                )
            )
        // validate details screen
        onView(withText(startsWith(createdByText))).check(matches(isDisplayed()))
        // navigate to user screen
        onView(withId(R.id.detail)).perform(click())
        // validate user screen
        onView(withText(startsWith(testItemTitlePrefix))).check(matches(isDisplayed()))
        //  navigate back
        onView(withContentDescription(context.getString(androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description))).perform(
            click()
        )
        // validate detail screen
        onView(withText(startsWith(createdByText))).check(matches(isDisplayed()))
    }
}