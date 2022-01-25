package com.ravnnerdery.photo_challenge.enlargedPhoto

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ravnnerdery.photo_challenge.R

class EnlargedPhotoFragment : Fragment() {

    companion object {
        fun newInstance() = EnlargedPhotoFragment()
    }

    private lateinit var viewModel: EnlargedPhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EnlargedPhotoViewModel::class.java)
        return inflater.inflate(R.layout.enlarged_photo_fragment, container, false)
    }

}