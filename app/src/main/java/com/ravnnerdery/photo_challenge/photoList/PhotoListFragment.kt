package com.ravnnerdery.photo_challenge.photoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.R
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.enlargedPhoto.EnlargedPhotoViewModel
import kotlinx.android.synthetic.main.photo_list_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {

    private val photoListViewModel: PhotoListViewModel by viewModel()
    private lateinit var binding: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.inflate(R.layout.photo_list_fragment, container, false)
        adapter = PhotosAdapter( this.context ,PhotoClickListener { id -> photoListViewModel.onPhotoClicked(id) })
        recyclerView = binding.photoListRecyclerView
        recyclerView.adapter = adapter

        photoListViewModel.allPhotos().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        photoListViewModel.navigateToSnapshot.observe(this, { photo ->
        photo?.let{
            this.findNavController().navigate(PhotoListFragmentDirections.actionPhotoListFragmentToEnlargedPhotoFragment(photo))
                    photoListViewModel.onSnapshotNavigated()
        }})

        return binding
    }

}