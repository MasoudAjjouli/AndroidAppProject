package com.example.myapp.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.UserInfoElement
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(): ViewModel() {

    private var _user = MutableLiveData<UserInfoElement>()
    val user: LiveData<UserInfoElement> = _user

    fun bind(userDetail: UserInfoElement){
       _user.value =  userDetail
    }


}