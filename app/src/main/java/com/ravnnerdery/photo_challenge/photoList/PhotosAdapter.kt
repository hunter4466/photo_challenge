package com.ravnnerdery.photo_challenge.photoList

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.core.content.MimeTypeFilter
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ravnnerdery.photo_challenge.R
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.databinding.PhotoViewReverseBinding
import com.ravnnerdery.photo_challenge.databinding.PhotoViewStraightBinding

class PhotosAdapter(val context: Context?, private val clickListener: PhotoClickListener) :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(PostListDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 1) {
            SubViewHolderStraight.from(parent)
        } else {
            SubViewHolderReverse.from(parent)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (context != null) {
            holder.bind(context, getItem(position), clickListener)
        }
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
        abstract fun bind(context: Context, item: Photo, clickListener: PhotoClickListener)
    }

    class SubViewHolderStraight private constructor(binding: PhotoViewStraightBinding) :
        ViewHolder(binding) {
        override fun bind(context: Context, item: Photo, clickListener: PhotoClickListener) {
            val binding = binding as PhotoViewStraightBinding
            binding.photo = item
            binding.clickListener = clickListener
            val cR = context.contentResolver
            val mime = MimeTypeMap.getSingleton()
            val uri = Uri.parse(item.thumbnailUrl)
            val type = mime.getExtensionFromMimeType(cR.getType(uri))
            println(type)
            Glide
                .with(context)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_background)
                .transition(DrawableTransitionOptions.withCrossFade(250))
                .into(binding.thumbNailfromList)

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
        override fun bind(context: Context, item: Photo, clickListener: PhotoClickListener) {
            val binding = binding as PhotoViewReverseBinding
            binding.photo = item
            binding.clickListener = clickListener
            val uri = Uri.parse(item.thumbnailUrl + ".png")
            Glide
                .with(context)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_background)
                .transition(DrawableTransitionOptions.withCrossFade(250))
                .into(binding.thumbNailfromList)

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