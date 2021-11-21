package com.wengelef.pixabaygallery.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.wengelef.pixabaygallery.ui.detail.viewmodel.*
import com.wengelef.pixabaygallery.R
import com.wengelef.pixabaygallery.databinding.FragmentDetailBinding
import com.wengelef.pixabaygallery.ui.detail.viewmodel.DetailViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        const val KEY_ID = "DetailFragment.KEY_ID"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getString(KEY_ID) ?: throw IllegalArgumentException("Missing argument $KEY_ID")
        val viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        val binding = FragmentDetailBinding.bind(view)

        viewModel.getDetail(id)

        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                Error ->
                    Snackbar.make(binding.root, "Oops", Snackbar.LENGTH_SHORT).show()

                is Result -> binding.apply {
                    image.load(state.photo.imageUrl) { crossfade(true) }
                    likes.text = state.photo.numberOfLikes.toString()
                    comments.text = state.photo.numberOfComments.toString()
                    downloads.text = state.photo.numberOfDownloads.toString()
                    username.text = state.photo.user
                    tags.text = state.photo.tags
                }
            }
        }
    }
}


