package com.example.a98811_lab_week05.model

import com.squareup.moshi.Json
data class ImageData(
    @field:Json(name = "url") val imageUrl: String,
    val breeds: List<CatBreedData>
)
