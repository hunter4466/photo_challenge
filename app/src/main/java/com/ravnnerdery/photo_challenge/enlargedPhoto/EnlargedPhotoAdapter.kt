package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.EnlargedPhotoBinding

class EnlargedPhotoAdapter(val context: Context?) :
    ListAdapter<Photo, EnlargedPhotoAdapter.ViewHolder>(EnlargedPhotoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (context != null) {
            holder.bind(context, item)
        }
    }

    class ViewHolder private constructor(private val binding: EnlargedPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: Photo) {
            binding.executePendingBindings()
            Glide.with(context).load(item.thumbnailUrl).into(binding.enlargedPhotoView)
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ViewHolder(EnlargedPhotoBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

}

class EnlargedPhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
}
