package com.abdullateif.dogpictures.ui.breed_images

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.core.breedImagesListMockProvider
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.data.repository.DogBreedImagesRepository
import com.abdullateif.dogpictures.data.repository.FavoriteImagesRepository
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
class DogBreedImagesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DogBreedImagesViewModel
    private val breedImagesRepository: DogBreedImagesRepository = mockk()
    private val favoriteImagesRepository: FavoriteImagesRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `loading state when fetching data`() = runTest {
        viewModel = DogBreedImagesViewModel(breedImagesRepository, favoriteImagesRepository)

        viewModel.fetchDogBreedImages("test")

        Truth.assertThat(viewModel.uiState.value).isEqualTo(
            DogBreedImagesState(uiState = UIState.LOADING)
        )
    }

    @Test
    fun `fetch breed images successfully and update state`() = runTest {
        coEvery { breedImagesRepository.fetchDogBreedImages("test") } returns Resource.Success(data = breedImagesListMockProvider())

        viewModel = DogBreedImagesViewModel(breedImagesRepository, favoriteImagesRepository)
        viewModel.fetchDogBreedImages("test")

        coVerify { breedImagesRepository.fetchDogBreedImages("test") }

        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.DATA)
    }

    @Test
    fun `fetch data with error message and update state`() = runTest {

        coEvery { breedImagesRepository.fetchDogBreedImages("test") } returns Resource.Error(message = "Network error!")

        viewModel = DogBreedImagesViewModel(breedImagesRepository, favoriteImagesRepository)
        viewModel.fetchDogBreedImages("test")

        coVerify { breedImagesRepository.fetchDogBreedImages("test") }

        Truth.assertThat(viewModel.uiState.value?.message).isNotNull()
        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.ERROR)
    }

    @Test
    fun `fetch data with an empty result`() {

        coEvery { breedImagesRepository.fetchDogBreedImages("test") } returns Resource.Success(data = emptyList())

        viewModel = DogBreedImagesViewModel(breedImagesRepository, favoriteImagesRepository)
        viewModel.fetchDogBreedImages("test")

        coVerify { breedImagesRepository.fetchDogBreedImages("test") }

        Truth.assertThat(viewModel.uiState.value?.images).isEmpty()
    }

    @Test
    fun `loading state when save images to favorite`() = runTest {
        viewModel = DogBreedImagesViewModel(breedImagesRepository, favoriteImagesRepository)

        viewModel.saveFavoriteImage(FavoriteImage(1, "url", "african"))

        Truth.assertThat(viewModel.uiState.value).isEqualTo(
            DogBreedImagesState(uiState = UIState.UPDATE)
        )
    }
}