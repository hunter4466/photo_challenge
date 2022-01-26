package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.EnlargedPhotoBinding
import com.squareup.picasso.Picasso

class EnlargedPhotoAdapter : ListAdapter<Photo, EnlargedPhotoAdapter.ViewHolder>(EnlargedPhotoDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor (private val binding: EnlargedPhotoBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Photo){
            Picasso.get().load(item.url).into(binding.enlargedPhotoView)
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ViewHolder(EnlargedPhotoBinding.inflate(layoutInflater, parent, false))
            }
        }
    }

}

class EnlargedPhotoDiffCallBack : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
}
