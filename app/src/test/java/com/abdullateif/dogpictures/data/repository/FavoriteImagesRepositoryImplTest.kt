package com.abdullateif.dogpictures.data.repository

import com.abdullateif.dogpictures.common.Resource
import com.abdullateif.dogpictures.core.breedImagesListMockProvider
import com.abdullateif.dogpictures.core.dogBreedImagesResponseMockProvider
import com.abdullateif.dogpictures.core.favoriteImagesListMockProvider
import com.abdullateif.dogpictures.data.local.DogBreedImagesLocalSource
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
class FavoriteImagesRepositoryImplTest   {
    private lateinit var repository: FavoriteImagesRepositoryImpl
    private var localSource = mockk<DogBreedImagesLocalSource>()
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        repository = FavoriteImagesRepositoryImpl(localSource, dispatcher)
    }

    @Test
    fun `fetch favorite images successfully by given mock data`() = runTest {
        coEvery { localSource.getAllImages() } returns favoriteImagesListMockProvider()

        val result = repository.getAllImages()

        coVerify {
            localSource.getAllImages()
        }

        Truth.assertThat(result).isInstanceOf(Resource.Success::class.java)
        Truth.assertThat(result.data).isNotNull()
    }

    @Test
    fun `fetch breeds should return error with message`() = runTest {

        coEvery { localSource.getAllImages() } throws IOException("Network error!")

        val result = repository.getAllImages()

        coVerify {
            localSource.getAllImages()
        }

        Truth.assertThat(result).isInstanceOf(Resource.Error::class.java)
        Truth.assertThat(result.message).isEqualTo("Network error!")
        Truth.assertThat(result.data).isNull()
    }
}