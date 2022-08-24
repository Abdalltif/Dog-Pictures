package com.abdullateif.dogpictures.ui.favorite_images

import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.FavoriteImage


data class FavoriteImagesState(
    val uiState: UIState = UIState.LOADING,
    val images: List<FavoriteImage>? = emptyList(),
    val breeds: List<String>? = emptyList(),
    val message: String? = null
)