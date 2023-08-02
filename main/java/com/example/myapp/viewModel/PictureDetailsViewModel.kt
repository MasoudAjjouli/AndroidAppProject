package com.example.myapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.models.PicsList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PictureDetailsViewModel @Inject constructor(): ViewModel() {

    private var _picture = MutableLiveData<PicsList>()
    val pic: LiveData<PicsList> = _picture

    fun bind(picDetails: PicsList){
        _picture.value =  picDetails
    }
}