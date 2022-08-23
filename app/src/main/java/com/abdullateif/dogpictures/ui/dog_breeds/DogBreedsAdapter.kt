package com.abdullateif.dogpictures.ui.dog_breeds

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.dogpictures.common.AppDiffUtil
import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.databinding.ListItemDogBreedBinding

class DogBreedsAdapter(
    private val breedsListener: OnBreedItemClick
) : RecyclerView.Adapter<DogBreedsAdapter.ViewHolder>() {

    private var dogBreeds: List<DogBreed> = emptyList()

    fun setList(newList: List<DogBreed>){
        val diffUtil = AppDiffUtil(dogBreeds, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        dogBreeds  = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemDogBreedBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dogBreeds[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dogBreeds.size

    inner class ViewHolder(private val binding: ListItemDogBreedBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: DogBreed) {
            binding.dogBreed = item
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                breedsListener.onDogBreedClick(dogBreeds[position])
        }
    }

    interface OnBreedItemClick {
        fun onDogBreedClick(dogBreed: DogBreed)
    }
}