package com.example.github.repositories

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.github.repositories.ui.adapters.RepositoryAdapter
import com.example.github.repositories.util.BaseTest
import org.hamcrest.Matchers.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BookmarkInstrumentedTest : BaseTest() {

    @Test
    fun testRepositoryBookmarkWorksFine() {
        performBookmark()
    }

    @Test
    fun testRepositoryBookmarkUpdatesOnMainList() {
        performBookmark()
        //  navigate back
        onView(withContentDescription(context.getString(androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description))).perform(
            click()
        )
        // validate the bookmark status in list
        onView(withId(R.id.repos_list))
            .check(
                matches(
                    hasItemAtPosition(
                        testItemIndex,
                        hasDescendant(withImageDrawable(R.drawable.baseline_bookmark_black_24))
                    )
                )
            )

    }

    @Test
    fun testRepositoryBookmarkUpdatesOnUserReposList() {
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
        // do bookmark
        performBookmark()
        //  navigate back
        onView(withContentDescription(context.getString(androidx.navigation.ui.R.string.nav_app_bar_navigate_up_description))).perform(
            click()
        )
        // validate the bookmark status in list
        onView(withId(R.id.repos_list))
            .check(
                matches(
                    hasItemAtPosition(
                        testItemIndex,
                        hasDescendant(withImageDrawable(R.drawable.baseline_bookmark_black_24))
                    )
                )
            )
    }


    private fun performBookmark() {
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
        // check book mark status
        onView(withId(R.id.image)).check(matches(withImageDrawable(R.drawable.baseline_bookmark_border_black_24)))
        // click to bookmark
        onView(withId(R.id.image)).perform(click())
        // check bookmark status
        onView(withId(R.id.image)).check(matches(withImageDrawable(R.drawable.baseline_bookmark_black_24)))

    }
}