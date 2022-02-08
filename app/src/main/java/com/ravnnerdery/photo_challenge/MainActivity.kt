package com.ravnnerdery.photo_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ravnnerdery.photo_challenge.photoList.PhotoListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}