package com.abdullateif.dogpictures.common

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.abdullateif.dogpictures.ui.breed_images.DogBreedImagesAdapter
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsAdapter
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsState
import com.abdullateif.dogpictures.ui.breed_images.DogBreedImagesState
import com.bumptech.glide.Glide

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context).load(url)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("breedsState")
    fun setRecyclerViewProperties(recyclerView: RecyclerView, state: DogBreedsState?) {
        if (recyclerView.adapter is DogBreedsAdapter) {
            val adapter = recyclerView.adapter as DogBreedsAdapter
            state?.let { dogBreedsState ->
                if (dogBreedsState.uiState == UIState.DATA)
                    adapter.setList(dogBreedsState.breeds as List<DogBreed>)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageState")
    fun setImagesRecyclerViewProperties(recyclerView: RecyclerView, state: DogBreedImagesState?) {
        if (recyclerView.adapter is DogBreedImagesAdapter) {
            val adapter = recyclerView.adapter as DogBreedImagesAdapter
            state?.let { imagesState ->
                if (imagesState.uiState == UIState.DATA)
                    adapter.setList(imagesState.images as List<DogBreedImage>)
            }
        }
    }
}