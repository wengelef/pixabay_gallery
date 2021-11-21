package com.wengelef.pixabaygallery.ui.list

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.wengelef.pixabaygallery.R
import com.wengelef.pixabaygallery.databinding.FragmentListBinding
import com.wengelef.pixabaygallery.ui.detail.DetailFragment
import com.wengelef.pixabaygallery.ui.list.adapter.PhotosAdapter
import com.wengelef.pixabaygallery.ui.list.viewmodel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

class ListFragment : Fragment(R.layout.fragment_list) {

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        val binding = FragmentListBinding.bind(view)

        val photosAdapter = PhotosAdapter(::itemClick)

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = photosAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }

        lifecycleScope.launchWhenStarted {
            binding.textInput.textChanges()
                .debounce(500L)
                .filter { it.isNotBlank() }
                .collect(viewModel::searchPhoto)
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                Idle -> viewModel.searchPhoto("fruits")
                Error -> {
                    binding.message.text = getString(R.string.this_is_an_error)
                    binding.message.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                }
                Loading -> {
                    binding.message.text = getString(R.string.this_is_loading)
                    binding.message.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                }
                is Result -> {
                    photosAdapter.submitList(state.photos)
                    binding.message.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun itemClick(id: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_yes)) { dialog, _ ->
                dialog.dismiss()

                findNavController()
                    .navigate(
                        R.id.action_listFragment_to_detailFragment,
                        bundleOf(DetailFragment.KEY_ID to id)
                    )
            }
            .setNegativeButton(getString(R.string.dialog_no)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun TextInputEditText.textChanges(): Flow<String> = callbackFlow {
    doAfterTextChanged { trySend(it.toString()) }
    awaitClose { removeTextChangedListener(null) }
}