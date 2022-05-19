package com.example.github.repositories.ui.utils

import android.widget.ImageView
import androidx.core.net.toUri
import com.example.github.repositories.R
import com.squareup.picasso.Picasso

object AppUtil {

    fun loadIcon(resource: Int, image: ImageView) {
        Picasso.get().load(resource).into(image)
    }

    fun loadImageFromUri(resource: String?, image: ImageView) {
        Picasso.get().load(resource?.toUri()).into(image)
    }

}