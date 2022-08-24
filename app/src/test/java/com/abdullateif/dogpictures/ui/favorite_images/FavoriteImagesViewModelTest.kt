package com.abdullateif.dogpictures.ui.favorite_images

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.core.breedListMockProvider
import com.abdullateif.dogpictures.core.favoriteImagesListMockProvider
import com.abdullateif.dogpictures.data.repository.FavoriteImagesRepository
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsState
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class FavoriteImagesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoriteImagesViewModel
    private val favoriteImagesRepository: FavoriteImagesRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `loading state when fetching data`() = runTest {
        viewModel = FavoriteImagesViewModel(favoriteImagesRepository)

        Truth.assertThat(viewModel.uiState.value).isEqualTo(
            FavoriteImagesState(uiState = UIState.LOADING)
        )
    }

    @Test
    fun `fetch data with error message and update state`() = runTest {
        coEvery { favoriteImagesRepository.getAllBreeds() } returns Resource.Error(message = "Network error!")

        viewModel = FavoriteImagesViewModel(favoriteImagesRepository)

        coVerify { favoriteImagesRepository.getAllBreeds() }

        Truth.assertThat(viewModel.uiState.value?.message).isNotNull()
        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.ERROR)
    }


    @Test
    fun `fetch favorite images successfully and update state`() = runTest {
        coEvery { favoriteImagesRepository.getAllImages() } returns Resource.Success(data = favoriteImagesListMockProvider())
        coEvery { favoriteImagesRepository.getAllBreeds() } returns Resource.Success(data = listOf("j", "c"))

        viewModel = FavoriteImagesViewModel(favoriteImagesRepository)

        coVerify { favoriteImagesRepository.getAllBreeds() }

        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.DATA)
    }
//
}