package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ravnnerdery.photo_challenge.R
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.EnlargedPhotoBinding

class EnlargedPhotoAdapter :
    ListAdapter<Photo, EnlargedPhotoAdapter.ViewHolder>(EnlargedPhotoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
            holder.bind(item)
    }

    class ViewHolder private constructor(private val binding: EnlargedPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind( item: Photo) {
            binding.executePendingBindings()
            val uri = GlideUrl(
                item.url, LazyHeaders.Builder()
                    .addHeader(
                        "User-Agent",
                        WebSettings.getDefaultUserAgent(binding.enlargedPhotoView.context)
                    )
                    .build()
            )
            Glide.with(binding.enlargedPhotoView.context)
                .load(uri)
                .placeholder(R.drawable.background_img)
                .transition(DrawableTransitionOptions.withCrossFade(150))
                .into(binding.enlargedPhotoView)
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
