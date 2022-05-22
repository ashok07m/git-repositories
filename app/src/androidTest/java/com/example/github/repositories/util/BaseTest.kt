package com.example.github.repositories.util

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.github.repositories.GitReposApp
import com.example.github.repositories.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule


abstract class BaseTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    protected lateinit var context: GitReposApp

    private var mIdlingResource: IdlingResource? = null

    /**
     * Use [to launch and get access to the activity.][ActivityScenario.onActivity]
     */
    @Before
    open fun registerIdlingResource() {

        context = ApplicationProvider.getApplicationContext()

        val activityScenario: ActivityScenario<*> =
            ActivityScenario.launch(MainActivity::class.java)
        activityScenario.onActivity {
            mIdlingResource = (it.applicationContext as GitReposApp).getIdlingResource()
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }


    @After
    open fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }


    protected fun isHaveLength(length: Int): TypeSafeMatcher<View?>? {
        return object : TypeSafeMatcher<View?>() {

            override fun matchesSafely(item: View?): Boolean {
                return (item as TextView).text.length == length
            }

            override fun describeTo(description: Description?) {
            }
        }
    }

    protected fun withImageDrawable(resourceId: Int): Matcher<View?>? {
        return object : BoundedMatcher<View?, ImageView>(ImageView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $resourceId")
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                return sameBitmap(imageView.context, imageView.drawable, resourceId)
            }
        }
    }

    private fun sameBitmap(context: Context, drawable: Drawable, resourceId: Int): Boolean {
        var drawable: Drawable? = drawable
        var otherDrawable: Drawable = context.getResources().getDrawable(resourceId)
        if (drawable == null || otherDrawable == null) {
            return false
        }
        if (drawable is StateListDrawable && otherDrawable is StateListDrawable) {
            drawable = drawable.getCurrent()
            otherDrawable = otherDrawable.getCurrent()
        }
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val otherBitmap = (otherDrawable as BitmapDrawable).bitmap
            return bitmap.sameAs(otherBitmap)
        }
        return false
    }

    fun hasItemAtPosition(position: Int, matcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description?) {
                description?.appendText("has item at position $position : ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(item: RecyclerView?): Boolean {
                val viewHolder = item?.findViewHolderForAdapterPosition(position)
                return matcher.matches(viewHolder?.itemView)
            }
        }
    }

}