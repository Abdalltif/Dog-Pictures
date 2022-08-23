package com.abdullateif.dogpictures.data.remote

import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.squareup.moshi.Json

data class DogBreedImagesResponse(
    @Json(name = "message")
    val message: List<String>,
    @Json(name = "status")
    val status: String,
) {

    fun toDogBreedImages() : List<DogBreedImage>{
        return this.message.map {
            DogBreedImage(imageUrl = it)
        }
    }

}
