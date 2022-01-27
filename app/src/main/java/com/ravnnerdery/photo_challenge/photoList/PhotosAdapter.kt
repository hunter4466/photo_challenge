package com.ravnnerdery.photo_challenge.photoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.PhotoViewReverseBinding
import com.ravnnerdery.photo_challenge.databinding.PhotoViewStraightBinding
import com.squareup.picasso.Picasso

class PhotosAdapter(private val clickListener: PhotoClickListener) :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(PostListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1) {
            SubViewHolderStraight.from(parent)
        } else {
            SubViewHolderReverse.from(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.id.toInt() % 2 == 0) {
            1
        } else {
            0
        }
    }

    abstract class ViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: Photo, clickListener: PhotoClickListener)
    }

    class SubViewHolderStraight private constructor(binding: PhotoViewStraightBinding) :
        ViewHolder(binding) {
        override fun bind(item: Photo, clickListener: PhotoClickListener) {
            val binding = binding as PhotoViewStraightBinding
            binding.photo = item
            Picasso.get().load(item.thumbnailUrl).into(binding.thumbNailfromList)
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoViewStraightBinding.inflate(layoutInflater, parent, false)
                return SubViewHolderStraight(binding)
            }
        }
    }

    class SubViewHolderReverse private constructor(binding: PhotoViewReverseBinding) :
        ViewHolder(binding) {
        override fun bind(item: Photo, clickListener: PhotoClickListener) {
            val binding = binding as PhotoViewReverseBinding
            binding.photo = item
            Picasso.get().load(item.thumbnailUrl).into(binding.thumbNailfromList)
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PhotoViewReverseBinding.inflate(layoutInflater, parent, false)
                return SubViewHolderReverse(binding)
            }
        }
    }

}

class PostListDiffCallBack : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }
}

class PhotoClickListener(val clickListener: (postId: Long) -> Unit) {
    fun onClick(photo: Photo) = clickListener(photo.id)
}