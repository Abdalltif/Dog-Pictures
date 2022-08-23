package com.abdullateif.dogpictures.common

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsAdapter
import com.abdullateif.dogpictures.ui.dog_breeds.DogBreedsState
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
}