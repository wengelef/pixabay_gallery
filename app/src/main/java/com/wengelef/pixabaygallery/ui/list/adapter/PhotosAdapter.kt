package com.wengelef.pixabaygallery.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.wengelef.pixabaygallery.data.model.Photo
import com.wengelef.pixabaygallery.databinding.ItemPhotoBinding

class PhotosAdapter(private val itemClick: (String) -> Unit) : ListAdapter<Photo, PhotoViewHolder>(
    PhotoDiffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        holder.binding.apply {
            root.setOnClickListener { itemClick(photo.id) }
            image.load(photo.previewUrl) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            user.text = photo.user
            tags.text = photo.tags
        }
    }
}