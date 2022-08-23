package com.abdullateif.dogpictures.data.remote

import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.data.model.DogSubBreed
import com.squareup.moshi.Json

data class DogBreedsResponse(
    @Json(name = "message")
    val message: Map<String, List<String>>,
    @Json(name = "status")
    val status: String,
) {
    fun toDogBreeds() : List<DogBreed>{
        return this.message.entries.map {
            val subBreed = it.value.map { sub ->
                DogSubBreed(sub)
            }
            DogBreed(it.key, subBreed)
        }
    }
}
