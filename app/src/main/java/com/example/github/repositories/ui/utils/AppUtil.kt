package com.example.github.repositories.ui.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.example.github.repositories.R
import com.example.github.repositories.core.data.MAX_CHAR_COUNT
import com.example.github.repositories.core.domain.Repository
import com.squareup.picasso.Picasso

object AppUtil {

    fun loadIcon(resource: Int, image: ImageView) {
        Picasso.get().load(resource).into(image)
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