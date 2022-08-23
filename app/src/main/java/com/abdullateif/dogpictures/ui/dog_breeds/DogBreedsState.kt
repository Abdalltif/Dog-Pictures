package com.abdullateif.dogpictures.ui.dog_breeds

import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.DogBreed


data class DogBreedsState(
    val uiState: UIState = UIState.LOADING,
    val breeds: List<DogBreed>? = emptyList(),
    val message: String? = null
)