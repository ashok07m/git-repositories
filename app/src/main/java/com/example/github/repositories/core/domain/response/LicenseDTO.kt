package com.example.github.repositories.core.domain.response

data class LicenseDTO(
    var key: String,
    var name: String,
    var spdx_id: String,
    var url: String,
    var node_id: String
)