package com.abdullateif.dogpictures.ui.dog_breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.repository.DogBreedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedsViewModel @Inject constructor(
    private val dogBreedsRepository: DogBreedsRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DogBreedsState>()
    val uiState: LiveData<DogBreedsState> = _uiState

    init {
        fetchDogBreeds()
    }

    fun fetchDogBreeds() {
        _uiState.value = DogBreedsState(
            uiState = UIState.LOADING
        )

        viewModelScope.launch {
            val resource = dogBreedsRepository.fetchDogBreeds()
            _uiState.value = when (resource) {
                is Resource.Success -> {
                    DogBreedsState(
                        uiState = UIState.DATA,
                        breeds = resource.data
                    )
                }
                is Resource.Error -> {
                    DogBreedsState(
                        uiState = UIState.ERROR,
                        message = resource.message
                    )
                }
            }
        }
    }
}