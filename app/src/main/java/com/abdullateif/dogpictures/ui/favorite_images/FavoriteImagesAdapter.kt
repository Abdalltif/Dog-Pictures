package com.abdullateif.dogpictures.ui.favorite_images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abdullateif.dogpictures.common.AppDiffUtil
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.databinding.ListItemFavImageBinding

class FavoriteImagesAdapter(
    private val listener: OnFavoriteImageItemClick,
): RecyclerView.Adapter<FavoriteImagesAdapter.ViewHolder>() {

    private var images: List<FavoriteImage> = emptyList()

    fun setList(newList: List<FavoriteImage>){
        val diffUtil = AppDiffUtil(images, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        images  = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemFavImageBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(
        private val binding: ListItemFavImageBinding
        ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: FavoriteImage) {
            binding.image = item
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onImageClick(images[position].imgUrl)
        }
    }

    interface OnFavoriteImageItemClick {
        fun onImageClick(imgUrl: String)
    }
}