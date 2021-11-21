package com.wengelef.pixabaygallery.ui.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wengelef.pixabaygallery.data.model.Photo

object PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem == newItem
}