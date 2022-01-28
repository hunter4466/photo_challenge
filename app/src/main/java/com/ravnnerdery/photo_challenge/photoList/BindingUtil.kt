package com.ravnnerdery.recyclechallenge.postlist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ravnnerdery.photo_challenge.database.tables.Photo

@BindingAdapter("capitalizeText")
fun TextView.setCapitalizedTitle(item: Photo?){
    item?.let{
        (item.title[0].uppercaseChar() + item.title.substring(1)).also { text = it }
    }
}
