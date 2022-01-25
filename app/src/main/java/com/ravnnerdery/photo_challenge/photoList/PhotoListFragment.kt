package com.ravnnerdery.photo_challenge.photoList

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import com.ravnnerdery.photo_challenge.R
import com.ravnnerdery.photo_challenge.database.tables.Photo
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {
    private val photoListViewModel: PhotoListViewModel by viewModel()
    private lateinit var photos: LiveData<List<Photo>>
    private lateinit var binding: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.inflate(R.layout.photo_list_fragment, container, false)
        photos = photoListViewModel.allPhotos
        photos.observe(viewLifecycleOwner, {
            println(it.size)
        })
        return binding
    }
}