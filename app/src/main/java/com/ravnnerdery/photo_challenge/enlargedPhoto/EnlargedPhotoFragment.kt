package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.ravnnerdery.photo_challenge.R
import kotlinx.android.synthetic.main.enlarged_photo_fragment.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.SnapHelper
import kotlin.properties.Delegates

class EnlargedPhotoFragment : Fragment() {

    private val enlargedPhotoViewModel: EnlargedPhotoViewModel by viewModel()
    private lateinit var binding: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EnlargedPhotoAdapter
    private lateinit var snapHelper: SnapHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = EnlargedPhotoFragmentArgs.fromBundle(requireArguments())
        binding = inflater.inflate(R.layout.enlarged_photo_fragment, container, false)
        recyclerView = binding.enlargedPhotoRecyclerView
        adapter = EnlargedPhotoAdapter(this.context)
        recyclerView.adapter = adapter
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        enlargedPhotoViewModel.allPhotos().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            recyclerView.scrollToPosition(args.id.toInt() - 1 )
        })

        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}