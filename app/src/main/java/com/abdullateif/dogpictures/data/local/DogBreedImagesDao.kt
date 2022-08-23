package com.abdullateif.dogpictures.data.local

import androidx.room.*
import com.abdullateif.dogpictures.data.model.FavoriteImage

@Dao
interface DogBreedImagesDao {
    @Query("SELECT * FROM favorites")
    fun getAllImages(): List<FavoriteImage>

    @Query("SELECT DISTINCT breed_name FROM favorites")
    fun getAllBreeds(): List<String>

    @Query("SELECT * FROM favorites WHERE breed_name = :breedName")
    fun getImagesByBreedName(breedName: String): List<FavoriteImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(image: FavoriteImage)

    @Query("DELETE FROM favorites WHERE image_url = :imgUrl")
    fun deleteImage(imgUrl: String)

    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE image_url = :imgUrl)")
    fun isImageExist(imgUrl: String) : Boolean
}