package com.ravnnerdery.photo_challenge.photoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ravnnerdery.photo_challenge.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {
    private lateinit var binding: View
    private val photoListViewModel: PhotoListViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photoListViewModel.allPhotos().observe(viewLifecycleOwner, {
            for (elm in it){
                print("<<<<<<<<<<<<<<ELM TITLE: ${elm.title}>>>>>>>>>>>>>>>>>>>")
            }
        })
        binding = inflater.inflate(R.layout.photo_list_fragment, container, false)
        return binding
    }
}