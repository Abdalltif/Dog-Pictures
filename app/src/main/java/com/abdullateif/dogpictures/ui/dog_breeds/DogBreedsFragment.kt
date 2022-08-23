package com.abdullateif.dogpictures.ui.dog_breeds

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullateif.dogpictures.R
import com.abdullateif.dogpictures.common.UIState
import com.abdullateif.dogpictures.data.model.DogBreed
import com.abdullateif.dogpictures.databinding.FragmentDogBreedsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DogBreedsFragment : Fragment(R.layout.fragment_dog_breeds), DogBreedsAdapter.OnBreedItemClick {
    private lateinit var binding: FragmentDogBreedsBinding
    private val viewModel: DogBreedsViewModel by viewModels()
    private val dogBreedsAdapter by lazy { DogBreedsAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDogBreedsBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        setupRecyclerView()
        setupStateObserver()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchDogBreeds()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dog_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_fav -> {
            openFavoriteImagesScreen()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = dogBreedsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { dogBreedsState ->
            when (dogBreedsState.uiState) {
                UIState.LOADING -> handleLoading()
                UIState.DATA -> handleData(dogBreedsState.breeds!!)
                UIState.ERROR -> handleError(dogBreedsState.message!!)
                UIState.IDLE -> {}
                UIState.UPDATE -> {}
            }
        }
    }

    private fun handleError(message: String) {
        binding.progressBar.isVisible = false
        if (dogBreedsAdapter.itemCount <= 0)
            binding.txtBreedsNoResults.isVisible = true
        else
            showErrorToast(message)
    }

    private fun handleData(breeds: List<DogBreed>) {
        binding.progressBar.isVisible = false
        binding.txtBreedsNoResults.isVisible = breeds.isEmpty()
    }

    private fun handleLoading() {
        binding.progressBar.isVisible = true
        binding.txtBreedsNoResults.isVisible = false
    }

    private fun showErrorToast(message:String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDogBreedClick(dogBreed: DogBreed) {
        openBreedImagesScreen(dogBreed.name)
    }

    private fun openBreedImagesScreen(breedName: String) {
        val action = DogBreedsFragmentDirections.actionDogBreedsFragmentToDogBreedImagesFragment(breedName)
        findNavController().navigate(action)
    }

    private fun openFavoriteImagesScreen() {
    }
}