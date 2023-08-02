package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapp.R
import com.example.myapp.databinding.PictureDetailsFragmentBinding
import com.example.myapp.viewModel.PictureDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureDetailsFragment: Fragment() {

    private val args: PictureDetailsFragmentArgs by navArgs()
    private lateinit var binding: PictureDetailsFragmentBinding
    private val pictureDetailsViewModel : PictureDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate<PictureDetailsFragmentBinding>(
            inflater,
            R.layout.picture_details_fragment,
            container,
            false
        ).apply {
           viewModel = pictureDetailsViewModel
        }
        initView()

        return binding.root

    }

    private fun initView() {
        binding.Picture.setImageURI(args.currentPicture?.picture)

    }

}