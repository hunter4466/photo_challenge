package com.ravnnerdery.photo_challenge.photoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var snapHelper: SnapHelper
    private lateinit var swipeContainer: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflater.inflate(R.layout.photo_list_fragment, container, false)
        adapter = PhotosAdapter( PhotoClickListener { id -> photoListViewModel.onPhotoClicked(id) })
        recyclerView = binding.photoListRecyclerView
        recyclerView.adapter = adapter
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        swipeContainer = binding.photoListSwypeContainer

        photoListViewModel.allPhotos().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            if(photoListViewModel.currentPosition != 0){
                photoListViewModel.currentPosition?.let { it1 -> recyclerView.scrollToPosition(it1) }
            }
        })

        photoListViewModel.navigateToSnapshot.observe(this, { photo ->
        photo?.let{
            this.findNavController().navigate(PhotoListFragmentDirections.actionPhotoListFragmentToEnlargedPhotoFragment(photo))
                    photoListViewModel.onSnapshotNavigated()
        }})

        swipeContainer.setOnRefreshListener {
            photoListViewModel.startRefreshingData()
            photoListViewModel.allPhotos().observe(viewLifecycleOwner, {
                swipeContainer.isRefreshing = false
            })
        }

        return binding
    }
    override fun onDestroy() {
        val layoutManager = recyclerView.layoutManager
        val snapView: View? = snapHelper.findSnapView(layoutManager)
        val snapPosition = snapView?.let { layoutManager?.getPosition(it) }
        photoListViewModel.currentPosition = snapPosition
        super.onDestroy()
    }

}