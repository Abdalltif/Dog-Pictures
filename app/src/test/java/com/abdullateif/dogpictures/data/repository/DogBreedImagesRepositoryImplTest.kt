package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.core.breedImagesListMockProvider
import com.abdullateif.dogpictures.core.breedListMockProvider
import com.abdullateif.dogpictures.core.dogBreedImagesResponseMockProvider
import com.abdullateif.dogpictures.core.dogBreedsResponseMockProvider
import com.abdullateif.dogpictures.data.remote.DogBreedsApi
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
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class DogBreedImagesRepositoryImplTest   {
    private lateinit var repository: DogBreedImagesRepositoryImpl
    private var dogBreedsApi = mockk<DogBreedsApi>()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = DogBreedImagesRepositoryImpl(dogBreedsApi, dispatcher)
    }

    @Test
    fun `fetch breed images successfully by given mock data`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreedImages("bulldog") } returns dogBreedImagesResponseMockProvider()
        coEvery { dogBreedsApi.fetchDogBreedImages("bulldog").toDogBreedImages() } returns breedImagesListMockProvider()

        val result = repository.fetchDogBreedImages("bulldog")

        coVerify {
            dogBreedsApi.fetchDogBreedImages("bulldog")
        }

        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(result.data).isNotNull()
    }

    @Test
    fun `fetch breed images should return empty list`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreedImages("bulldog") } returns dogBreedImagesResponseMockProvider()
        coEvery { dogBreedsApi.fetchDogBreedImages("bulldog").toDogBreedImages() } returns emptyList()

        val result = repository.fetchDogBreedImages("bulldog")

        coVerify {
            dogBreedsApi.fetchDogBreedImages("bulldog")
        }

        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(result.data?.size).isEqualTo(0)
    }

    @Test
    fun `fetch breed images should return error with message`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreedImages("bulldog") } throws IOException("Network error!")

        val result = repository.fetchDogBreedImages("bulldog")

        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(result.message).isEqualTo("Network error!")
        Truth.assertThat(result.data).isNull()
    }
}