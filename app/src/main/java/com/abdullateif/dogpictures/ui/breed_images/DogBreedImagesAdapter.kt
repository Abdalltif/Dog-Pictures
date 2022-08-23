package com.abdullateif.dogpictures.ui.breed_images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.dogpictures.common.AppDiffUtil
import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.abdullateif.dogpictures.databinding.ListItemBreedImageBinding

class DogBreedImagesAdapter(
    private val listener: OnImageItemClick,
    private val breedName: String
) : RecyclerView.Adapter<DogBreedImagesAdapter.ViewHolder>() {

    private var breedImages: List<DogBreedImage> = emptyList()

    fun setList(newList: List<DogBreedImage>){
        val diffUtil = AppDiffUtil(breedImages, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        breedImages  = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemBreedImageBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = breedImages[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = breedImages.size

    inner class ViewHolder(private val binding: ListItemBreedImageBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: DogBreedImage) {
            binding.dogBreedImage = item
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onBreedImageClick(breedImages[position], breedName)
        }
    }

    interface OnImageItemClick {
        fun onBreedImageClick(dogBreedImage: DogBreedImage, breedName: String)
    }
}