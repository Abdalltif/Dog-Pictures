package com.abdullateif.dogpictures.ui.breed_images

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.data.repository.DogBreedImagesRepository
import com.abdullateif.dogpictures.data.repository.FavoriteImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogBreedImagesViewModel @Inject constructor(
    private val breedImagesRepository: DogBreedImagesRepository,
    private val favoriteImagesRepository: FavoriteImagesRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DogBreedImagesState>()
    val uiState: LiveData<DogBreedImagesState> = _uiState

    fun fetchDogBreedImages(breedName: String) {
        _uiState.value = DogBreedImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch {
            val resource = breedImagesRepository.fetchDogBreedImages(breedName)
            _uiState.value = when (resource) {
                is Resource.Success -> {
                    DogBreedImagesState(
                        uiState = UIState.DATA,
                        images = resource.data
                    )
                }
                is Resource.Error -> {
                    DogBreedImagesState(
                        uiState = UIState.ERROR,
                        message = resource.message
                    )
                }
            }
        }
    }

    fun favImage(image: FavoriteImage){
        _uiState.value = DogBreedImagesState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch(Dispatchers.IO) {
            val message = favoriteImagesRepository.saveFavoriteImage(image)
            withContext(Dispatchers.Main) {
                _uiState.value = DogBreedImagesState(
                    uiState = UIState.UPDATE,
                    message = message
                )
            }
        }
    }
}