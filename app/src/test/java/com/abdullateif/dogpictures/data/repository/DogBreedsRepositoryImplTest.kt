package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.core.breedListMockProvider
import com.abdullateif.dogpictures.core.dogBreedsResponseMockProvider
import com.abdullateif.dogpictures.data.remote.DogBreedsApi
import com.google.common.truth.Truth.assertThat
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
class DogBreedsRepositoryImplTest  {
    private lateinit var repository: DogBreedsRepositoryImpl
    private var dogBreedsApi = mockk<DogBreedsApi>()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = DogBreedsRepositoryImpl(dogBreedsApi, dispatcher)
    }

    @Test
    fun `fetch all breeds successfully by given mock data`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreeds() } returns dogBreedsResponseMockProvider()
        coEvery { dogBreedsApi.fetchDogBreeds().toDogBreeds() } returns breedListMockProvider()

        val result = repository.fetchDogBreeds()

        coVerify {
            dogBreedsApi.fetchDogBreeds()
        }

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isNotNull()
    }

    @Test
    fun `fetch breeds should return empty list`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreeds() } returns dogBreedsResponseMockProvider()
        coEvery { dogBreedsApi.fetchDogBreeds().toDogBreeds() } returns emptyList()

        val result = repository.fetchDogBreeds()

        coVerify {
            dogBreedsApi.fetchDogBreeds()
        }

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data?.size).isEqualTo(0)
    }

    @Test
    fun `fetch breeds should return 5 items`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreeds() } returns dogBreedsResponseMockProvider()
        coEvery { dogBreedsApi.fetchDogBreeds().toDogBreeds() } returns breedListMockProvider()

        val result = repository.fetchDogBreeds()

        coVerify {
            dogBreedsApi.fetchDogBreeds()
        }

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data?.size).isEqualTo(5)
    }

    @Test
    fun `fetch breeds should return error with message`() = runTest {
        coEvery { dogBreedsApi.fetchDogBreeds() } throws IOException("Network error!")

        val result = repository.fetchDogBreeds()

        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.message).isEqualTo("Network error!")
        assertThat(result.data).isNull()
    }
}