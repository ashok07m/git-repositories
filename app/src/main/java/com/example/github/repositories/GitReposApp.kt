package com.example.github.repositories

import android.app.Application
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.test.espresso.IdlingResource
import com.example.github.repositories.ui.idlingResource.SimpleIdlingResource
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GitReposApp : Application() {


    // The Idling Resource which will be null in production.
    @Nullable
    private var mIdlingResource: SimpleIdlingResource? = null


    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource
    }
}