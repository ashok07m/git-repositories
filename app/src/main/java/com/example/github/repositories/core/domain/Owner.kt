package com.example.github.repositories.core.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    var login: String?,
    var id: Int?,
    var avatar_url: String?
) : Parcelable