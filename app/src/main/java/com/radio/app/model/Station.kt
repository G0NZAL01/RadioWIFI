package com.radio.app.model

data class Station(
    val name: String,
    val url: String,
    val genre: String,
    val color: Int,
    val imageUrl: String? = null
)
