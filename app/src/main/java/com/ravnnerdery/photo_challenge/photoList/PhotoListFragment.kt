package com.ravnnerdery.photo_challenge.photoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.ravnnerdery.photo_challenge.databinding.PhotoListFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {

    private val photoListViewModel: PhotoListViewModel by viewModel()
    private var binding: PhotoListFragmentBinding? = null
    private lateinit var adapter: PhotosAdapter
    private lateinit var snapHelper: SnapHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotoListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.let {
            adapter = PhotosAdapter( PhotoClickListener { id -> photoListViewModel.onPhotoClicked(id) })
            it.photoListRecyclerView.adapter = adapter
            snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(it.photoListRecyclerView)
            it.photoListSwypeContainer.setOnRefreshListener {
                photoListViewModel.startRefreshingData()
            }
        }

        photoListViewModel.allPhotos().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            if(photoListViewModel.currentPosition != 0){
                photoListViewModel.currentPosition?.let { it1 -> binding?.photoListRecyclerView?.scrollToPosition(it1) }
            }
            binding?.photoListSwypeContainer?.isRefreshing = false
        })

        photoListViewModel.navigateToSnapshot.observe(this, { photo ->
            photo?.let{
                this.findNavController().navigate(PhotoListFragmentDirections.actionPhotoListFragmentToEnlargedPhotoFragment(photo))
                photoListViewModel.onSnapshotNavigated()
            }})
    }
    override fun onDestroy() {
        binding?.photoListRecyclerView?.layoutManager?.let {
            val snapView: View? = snapHelper.findSnapView(it)
            val snapPosition = snapView?.let { position -> it.getPosition(position) }
            photoListViewModel.currentPosition = snapPosition
        }
        super.onDestroy()
    }

}