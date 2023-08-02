package com.example.myapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.models.PicsList
import com.example.myapp.models.UserInfo
import com.example.myapp.network.RetroInstance
import com.example.myapp.network.RetroService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor() : ViewModel() {
    private val recyclerListLiveData = MutableLiveData<UserInfo>()
    private val recyclerListLiveDataPics = MutableLiveData<PicsList>()

    fun getRecyclerListObserver(): LiveData<UserInfo> {
        return recyclerListLiveData
    }

    fun getPicsListObserver(): LiveData<PicsList> {
        return recyclerListLiveDataPics
    }



    fun makeApiCall() {
        viewModelScope.launch {
            try {
                val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
                val responce = retroInstance.getDataFromApi()
                recyclerListLiveData.value = responce
                print("")
            } catch (e: Exception) {
                print("")
            }

        }
    }
}