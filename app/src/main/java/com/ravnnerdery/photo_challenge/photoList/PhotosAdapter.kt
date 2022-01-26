package com.ravnnerdery.photo_challenge.photoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.PhotoViewStraightBinding
import com.squareup.picasso.Picasso

class PhotosAdapter(private val clickListener: PhotoClickListener): ListAdapter<Photo, PhotosAdapter.ViewHolder>(PostListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor (private val binding: PhotoViewStraightBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Photo, clickListener: PhotoClickListener){
            binding
            binding.photo = item
            Picasso.get().load(item.thumbnailUrl).into(binding.thumbNailfromList)
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                return ViewHolder(PhotoViewStraightBinding.inflate(layoutInflater, parent, false))
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