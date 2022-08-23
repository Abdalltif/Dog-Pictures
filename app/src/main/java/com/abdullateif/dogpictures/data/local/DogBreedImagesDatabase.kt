package com.abdullateif.dogpictures.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abdullateif.dogpictures.data.model.FavoriteImage


@Database(entities = [FavoriteImage::class], version = 2, exportSchema = true)
abstract class DogBreedImagesDatabase : RoomDatabase() {

    abstract fun dogBreedImagesDao(): DogBreedImagesDao

    companion object {
        private var INSTANCE: DogBreedImagesDatabase? = null

        fun getDatabase(context: Context): DogBreedImagesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null )
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogBreedImagesDatabase::class.java,
                    "dog_breeds"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}