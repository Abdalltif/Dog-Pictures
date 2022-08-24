package com.abdullateif.dogpictures.ui.favorite_images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.repository.FavoriteImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoriteImagesViewModel @Inject constructor(
    private val favoriteImagesRepository: FavoriteImagesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<FavoriteImagesState>()
    val uiState: LiveData<FavoriteImagesState> = _uiState

    init {
        getBreedsFilter()
    }

    private fun getFavoriteImages() {
        _uiState.value = FavoriteImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch {
            val resource = favoriteImagesRepository.getAllImages()
            _uiState.value = when (resource) {
                is Resource.Success -> {
                    FavoriteImagesState(
                        uiState = UIState.DATA,
                        images = resource.data
                    )
                }
                is Resource.Error -> {
                    FavoriteImagesState(
                        uiState = UIState.ERROR,
                        message = resource.message
                    )
                }
            }
        }
    }

    fun getFavoriteImagesByBreed(breedName: String) {
        _uiState.value = FavoriteImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch {
            val resource = if (breedName == "All")
                favoriteImagesRepository.getAllImages()
            else
                favoriteImagesRepository.getImagesByBreedName(breedName)
            _uiState.value = when (resource) {
                is Resource.Success -> {
                    FavoriteImagesState(
                        uiState = UIState.DATA,
                        images = resource.data
                    )
                }
                is Resource.Error -> {
                    FavoriteImagesState(
                        uiState = UIState.ERROR,
                        message = resource.message
                    )
                }
            }
        }
    }

    private fun getBreedsFilter() {
        _uiState.value = FavoriteImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch(Dispatchers.IO) {
            val resource = favoriteImagesRepository.getAllBreeds()
            withContext(Dispatchers.Main) {
                _uiState.value = when (resource) {
                    is Resource.Success -> {
                        getFavoriteImages()
                        FavoriteImagesState(
                            uiState = UIState.UPDATE,
                            breeds = resource.data
                        )
                    }
                    is Resource.Error -> {
                        FavoriteImagesState(
                            uiState = UIState.ERROR,
                            message = resource.message
                        )
                    }
                }
            }
        }
    }

    fun unFavImage(imgUrl: String){
        _uiState.value = FavoriteImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                favoriteImagesRepository.unFavoriteImage(imgUrl)
            }.await()
            getBreedsFilter()
        }
    }
}