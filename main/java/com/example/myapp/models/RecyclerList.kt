package com.example.myapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

typealias UserInfo = List<UserInfoElement>


val USER_ID = "userExtra"

@Parcelize
data class UserInfoElement(
    val id: Long?,
    val bio: String?,
    val address: String?,
    @SerializedName("Friends")
    val friends: String?,
    var fullName: String?,
    val jobTitle: String?,
    val interests: String?,
    val phoneNumber: String?,
    val emailAddress: String?
) : Parcelable

enum class Friends {
    Age,
    Email,
    Name,
    Phone
}

