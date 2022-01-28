package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.ravnnerdery.photo_challenge.databinding.EnlargedPhotoFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnlargedPhotoFragment : Fragment() {

    private val enlargedPhotoViewModel: EnlargedPhotoViewModel by viewModel()
    private lateinit var binding: EnlargedPhotoFragmentBinding
    private lateinit var adapter: EnlargedPhotoAdapter
    private lateinit var snapHelper: SnapHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EnlargedPhotoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = EnlargedPhotoFragmentArgs.fromBundle(requireArguments())
        adapter = EnlargedPhotoAdapter()
        binding.enlargedPhotoRecyclerView.adapter = adapter
        snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.enlargedPhotoRecyclerView)
        enlargedPhotoViewModel.allPhotos().observe(viewLifecycleOwner) {
            Log.wtf("JAAC", it.toString())
            adapter.submitList(it)
            if (enlargedPhotoViewModel.currentPosition == 0) {
                val argPosition = args.id.toInt() - 1
                binding.enlargedPhotoRecyclerView.scrollToPosition(argPosition)
                enlargedPhotoViewModel.currentPosition = argPosition
            } else {
                enlargedPhotoViewModel.currentPosition?.let { position ->
                    binding.enlargedPhotoRecyclerView.scrollToPosition(position)
                }
            }
        }
    }


    override fun onDestroy() {
        binding.enlargedPhotoRecyclerView?.layoutManager?.let {
            val snapView: View? = snapHelper.findSnapView(binding.enlargedPhotoRecyclerView.layoutManager)
            val snapPosition = snapView?.let { binding.enlargedPhotoRecyclerView.layoutManager?.getPosition(it) }
            enlargedPhotoViewModel.currentPosition = snapPosition
        }
        super.onDestroy()
    }

}