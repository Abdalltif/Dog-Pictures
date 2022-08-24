package com.abdullateif.dogpictures.ui.dog_breeds

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.core.breedListMockProvider
import com.abdullateif.dogpictures.data.repository.DogBreedsRepository
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
class DogBreedsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: DogBreedsViewModel
    private val dogBreedsRepository: DogBreedsRepository = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @Test
    fun `loading state when fetching data`() = runTest {
        viewModel = DogBreedsViewModel(dogBreedsRepository)

        Truth.assertThat(viewModel.uiState.value).isEqualTo(
            DogBreedsState(uiState = UIState.LOADING)
        )
    }

    @Test
    fun `fetch breeds successfully and update state`() = runTest {
        coEvery { dogBreedsRepository.fetchDogBreeds() } returns Resource.Success(data = breedListMockProvider())

        viewModel = DogBreedsViewModel(dogBreedsRepository)

        coVerify { dogBreedsRepository.fetchDogBreeds() }

        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.DATA)
    }

    @Test
    fun `fetch data with error message and update state`() = runTest {
        coEvery { dogBreedsRepository.fetchDogBreeds() } returns Resource.Error(message = "Network error!")

        viewModel = DogBreedsViewModel(dogBreedsRepository)

        coVerify { dogBreedsRepository.fetchDogBreeds() }

        Truth.assertThat(viewModel.uiState.value?.message).isNotNull()
        Truth.assertThat(viewModel.uiState.value?.uiState).isEqualTo(UIState.ERROR)
    }

    @Test
    fun `fetch data with an empty result`() {
        coEvery { dogBreedsRepository.fetchDogBreeds() } returns Resource.Success(data = emptyList())

        viewModel = DogBreedsViewModel(dogBreedsRepository)

        coVerify { dogBreedsRepository.fetchDogBreeds() }

        Truth.assertThat(viewModel.uiState.value?.breeds).isEmpty()
    }
}