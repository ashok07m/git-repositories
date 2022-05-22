package com.example.github.repositories.ui.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.example.github.repositories.GitReposApp
import com.example.github.repositories.R
import com.example.github.repositories.core.data.MAX_CHAR_COUNT
import com.example.github.repositories.core.domain.Repository
import com.example.github.repositories.ui.idlingResource.SimpleIdlingResource
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

object AppUtil {

    fun loadIcon(resource: Int, image: ImageView) {
        val idling = (image.context.applicationContext as GitReposApp).getIdlingResource()
        idling?.let {
            (it as SimpleIdlingResource).setIdleState(false)
        }
        Picasso.get().load(resource).into(image, object: Callback{
            override fun onSuccess() {
                idling?.let {
                    (it as SimpleIdlingResource).setIdleState(true)
                }
            }

            override fun onError(e: Exception?) {
                idling?.let {
                    (it as SimpleIdlingResource).setIdleState(true)
                }
            }

        })
    }

    fun loadImageFromUri(resource: String?, image: ImageView) {
        Picasso.get().load(resource?.toUri()).into(image)
    }

    fun addEllipsesToDescriptionField(repository: Repository, ellipses: String): String {
        return if (repository.description?.length ?: 0 > MAX_CHAR_COUNT)
            repository.description?.take(MAX_CHAR_COUNT).plus(ellipses)
        else repository.description ?: ""
    }
}