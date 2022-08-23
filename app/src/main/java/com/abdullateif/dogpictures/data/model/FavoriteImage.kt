package com.abdullateif.dogpictures.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abdullateif.dogpictures.data.model.BaseModel

@Entity(tableName = "favorites")
data class FavoriteImage (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "image_url") val imgUrl: String,
    @ColumnInfo(name = "breed_name") val breedName: String
) : BaseModel
