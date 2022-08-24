package com.abdullateif.dogpictures.ui.breed_images

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullateif.dogpictures.R
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.DogBreedImage
import com.abdullateif.dogpictures.data.model.FavoriteImage
import com.abdullateif.dogpictures.databinding.FragmentDogBreedImagesBinding
import com.abdullateif.dogpictures.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogBreedImagesFragment : Fragment(R.layout.fragment_dog_breed_images), DogBreedImagesAdapter.OnImageItemClick  {
    private lateinit var binding: FragmentDogBreedImagesBinding
    private val viewModel: DogBreedImagesViewModel by viewModels()
    private val args: DogBreedImagesFragmentArgs by navArgs()
    private val breedImagesAdapter by lazy { DogBreedImagesAdapter(this, args.breedName) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogBreedImagesBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        val actionBar = (activity as MainActivity).supportActionBar
        actionBar?.title = "${args.breedName} pictures"

        setupRecyclerView()
        setupStateObserver()

        viewModel.fetchDogBreedImages(args.breedName)

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvImages.apply {
            adapter = breedImagesAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun setupStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { imagesState ->
            when (imagesState.uiState) {
                UIState.LOADING -> handleLoading()
                UIState.DATA -> handleData(imagesState.images!!)
                UIState.ERROR -> handleError(imagesState.message!!)
                UIState.IDLE -> {}
                UIState.UPDATE -> {
                    binding.progressBarImages.isVisible = false
                    showFavoriteUpdateToast(imagesState.message)
                }
            }
        }
    }

    private fun showFavoriteUpdateToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun handleError(message: String) {
        binding.progressBarImages.isVisible = false
        binding.txtImagesNoResults.isVisible = true
        showErrorToast(message)
    }

    private fun handleData(images: List<DogBreedImage>) {
        binding.progressBarImages.isVisible = false
        binding.txtImagesNoResults.isVisible = images.isEmpty()
    }

    private fun handleLoading() {
        binding.progressBarImages.isVisible = true
        binding.txtImagesNoResults.isVisible = false
    }

    private fun showErrorToast(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onBreedImageClick(dogBreedImage: DogBreedImage, breedName: String) {
        val image = FavoriteImage(null, dogBreedImage.imageUrl, breedName)
        showDialog(image)
    }

    private fun showDialog(favImage: FavoriteImage) {
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(getString(R.string.save_to_favorites))
                setPositiveButton(getString(R.string.ok)) { _, _ ->
                    viewModel.saveFavoriteImage(favImage)
                }
                setNegativeButton(getString(R.string.cancel)) { dialog, id ->
                    dialog.cancel()
                }
            }
            builder.create()
        }
        alertDialog!!.show()
    }
}