package com.example.pictureofdays.ui.extensions.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pictureofdays.R
import com.example.pictureofdays.databinding.FragmentHomeBinding
import com.example.pictureofdays.main.model.Repository.RepositoryImpl
import com.example.pictureofdays.ui.extensions.visible
import com.example.pictureofdays.viewBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        const val MAX_LINES = 5
    }

    private val viewBinding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(RepositoryImpl)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        homeViewModel.errorLiveData.observe(viewLifecycleOwner) { it ->
            val error = it ?: return@observe
            viewBinding.progress.visible { false }

            Snackbar
                .make(
                    viewBinding.root,
                    error,
                    Snackbar.LENGTH_INDEFINITE
                )
                .setAction(getString(R.string.repeat_text)) { homeViewModel.getPhoto() }
                .also { it ->
                    it.view.also {
                        (it.findViewById(com.google.android.material.R.id.snackbar_text) as TextView?)?.maxLines =
                            MAX_LINES
                    }
                }
                .show()
        }

        homeViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinding.progress.visible { it }
        }

        homeViewModel.photoLiveData.observe(viewLifecycleOwner) { it ->
            it?.title?.let {
                viewBinding.title.text = it
            }

            it?.photoUrl?.let { url ->
                Glide.with(viewBinding.photo)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .error(R.drawable.ic_no_image)
                    .into(viewBinding.photo)
            }

            it?.explanation?.let {
                viewBinding.description.text = it
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            homeViewModel.getPhoto()
        }
    }
}