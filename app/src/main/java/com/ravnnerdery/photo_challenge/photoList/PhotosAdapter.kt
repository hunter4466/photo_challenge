package com.ravnnerdery.photo_challenge.photoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.PhotoViewBinding


class PhotosAdapter(private val clickListener: PhotoClickListener): ListAdapter<Photo, PhotosAdapter.ViewHolder>(PostListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor (private val binding: PhotoViewBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Photo, clickListener: PhotoClickListener){
            binding.photo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}

class PostListDiffCallBack : DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
}

class PhotoClickListener(val clickListener: (postId: Long) -> Unit){
    fun onClick(post: Photo) = clickListener(post.id)
}