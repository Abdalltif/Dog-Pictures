package com.abdullateif.dogpictures.ui.favorite_images

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullateif.dogpictures.R
import com.abdullateif.dogpictures.R.*
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.databinding.FragmentFavoriteImagesBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteImagesFragment : Fragment(layout.fragment_favorite_images), FavoriteImagesAdapter.OnFavoriteImageItemClick {
    private lateinit var binding: FragmentFavoriteImagesBinding
    private val viewModel: FavoriteImagesViewModel by viewModels()
    private val favoriteImagesAdapter by lazy { FavoriteImagesAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteImagesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setupRecyclerView()
        setupStateObserver()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteImages.apply {
            adapter = favoriteImagesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun setupStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { imagesState ->
            when (imagesState.uiState) {
                UIState.LOADING -> handleLoading()
                UIState.DATA -> handleData(imagesState)
                UIState.ERROR -> handleError(imagesState.message!!)
                UIState.IDLE -> {}
                UIState.UPDATE -> handleUpdate(imagesState.breeds!!)
            }
        }
    }

    private fun handleData(imagesState: FavoriteImagesState) {
        binding.progressBarFavorite.isVisible = false
        binding.txtFavNoResults.isVisible = imagesState.images!!.isEmpty()
    }

    private fun handleUpdate(breeds: List<String>) {
        if (breeds.isNotEmpty()) {
            binding.chipGroup.isVisible = true
            handleFilters(breeds = breeds)
        }
    }

    private fun handleFilters(breeds: List<String>) {
        addChip(getString(string.all))
        for (name in breeds)
            addChip(name)
    }

    private fun addChip(text: String) {
        val chip = Chip(activity)
        chip.text = text
        chip.setOnClickListener {
            viewModel.getFavoriteImagesByBreed(text)
        }
        binding.chipGroup.addView(chip)
    }

    private fun handleError(message: String) {
        binding.progressBarFavorite.isVisible = false
        binding.txtFavNoResults.isVisible = true
        showErrorToast(message)
    }

    private fun handleLoading() {
        binding.progressBarFavorite.isVisible = true
        binding.txtFavNoResults.isVisible = false
    }

    private fun showErrorToast(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onImageClick(imgUrl: String) {
        showDialog(imgUrl)
    }

    private fun showDialog(imgUrl: String) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(getString(string.remove_image))
                setPositiveButton(string.ok) { _, _ ->
                    viewModel.unFavImage(imgUrl)
                    binding.chipGroup.removeAllViews()
                }
                setNegativeButton(string.cancel) { dialog, _ ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
        alertDialog!!.show()
    }
}