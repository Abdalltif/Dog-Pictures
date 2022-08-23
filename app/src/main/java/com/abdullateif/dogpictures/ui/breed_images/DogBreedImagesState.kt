package com.abdullateif.dogpictures.ui.breed_images

import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.DogBreedImage


data class DogBreedImagesState(
    val uiState: UIState = UIState.LOADING,
    val images: List<DogBreedImage>? = emptyList(),
    val message: String? = null
)