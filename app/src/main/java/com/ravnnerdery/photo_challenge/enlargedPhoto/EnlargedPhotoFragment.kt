package com.ravnnerdery.photo_challenge.enlargedPhoto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.SnapHelper
import com.ravnnerdery.photo_challenge.databinding.EnlargedPhotoFragmentBinding

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
        binding.let{
            adapter = EnlargedPhotoAdapter()
            it.enlargedPhotoRecyclerView.adapter = adapter
            snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(it.enlargedPhotoRecyclerView)
        }

        enlargedPhotoViewModel.allPhotos().observe(viewLifecycleOwner, {
            adapter.submitList(it)
            if(enlargedPhotoViewModel.currentPosition == 0){
                val argPosition = args.id.toInt() -1
                binding.enlargedPhotoRecyclerView.scrollToPosition(argPosition)
                enlargedPhotoViewModel.currentPosition = argPosition
            } else {
                enlargedPhotoViewModel.currentPosition?.let { it -> binding.enlargedPhotoRecyclerView.scrollToPosition(it) }
            }
        })

    }

    override fun onDestroy() {
        binding.enlargedPhotoRecyclerView.layoutManager?.let { it ->
            val snapView: View? = snapHelper.findSnapView(it)
            val snapPosition = snapView?.let { position -> it.getPosition(position) }
            enlargedPhotoViewModel.currentPosition = snapPosition
        }

        super.onDestroy()
    }

}