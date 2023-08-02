package com.example.myapp.models

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


var PicInfo = mutableListOf<PicsList>()


@Parcelize
data class PicsList(

 val name: String?,
 val picture: Uri?,

 ) : Parcelable



