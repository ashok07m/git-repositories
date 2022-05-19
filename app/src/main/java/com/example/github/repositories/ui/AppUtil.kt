package com.example.github.repositories.ui

import android.widget.ImageView
import com.example.github.repositories.R
import com.squareup.picasso.Picasso

object AppUtil {

    fun loadIcon(resource: Int, image: ImageView) {
        Picasso.get().load(resource).into(image)
    }
}