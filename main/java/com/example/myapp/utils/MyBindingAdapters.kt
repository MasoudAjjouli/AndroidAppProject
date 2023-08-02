package com.example.myapp.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

@BindingAdapter("loadUri")
fun ImageView.loadImageFromUri(picUri: Uri){
        this.load(picUri)
}







