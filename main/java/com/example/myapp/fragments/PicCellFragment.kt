package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.PicCellFragmentBinding


class PicCellFragment : Fragment() {

    private lateinit var binding: PicCellFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate<PicCellFragmentBinding>(
            inflater,
            R.layout.yellow_fragment, container, false
        )
        return binding.root
    }

}