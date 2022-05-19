package com.example.github.repositories.core.domain

import android.os.Parcelable
import com.example.github.repositories.core.domain.response.OwnerDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repository(
    val id: Int?,
    var name: String?,
    val full_name: String?,
    val private: Boolean?,
    val owner: Owner?,
    val html_url: String?,
    val description: String?,
    var created_at: String?,
    var isBookMarked: Boolean = false
) : Parcelable