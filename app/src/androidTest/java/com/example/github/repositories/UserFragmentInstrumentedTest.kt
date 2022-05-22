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
class UserFragmentInstrumentedTest : BaseTest() {

    private val testItemIndex = 1
    private val testItemTitlePrefix = "# 1"

    @Test
    fun testUserScreenHasAllDetails() {
        val createdByText = context.getString(R.string.label_created_by)
        val twitterHandle = context.getString(R.string.label_twitter_handle)
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
        onView(withId(R.id.imageThumb)).check(matches(isDisplayed()))
        onView(withId(R.id.url)).check(matches(isDisplayed()))
        onView(withId(R.id.detail)).check(matches(isDisplayed()))
        onView(withText(startsWith(twitterHandle))).check(matches(isDisplayed()))

    }
}