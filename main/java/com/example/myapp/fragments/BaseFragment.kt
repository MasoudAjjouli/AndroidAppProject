package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.myapp.utils.ColorsViewPagerAdapter
import com.example.myapp.R
import com.example.myapp.databinding.ActivityWelcomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BaseFragment : Fragment() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var viewPagerAdapter: ColorsViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<ActivityWelcomeBinding>(
            inflater, R.layout.activity_welcome, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        viewPagerAdapter = ColorsViewPagerAdapter(this@BaseFragment)
        with(binding) {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tlColorFragment, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Red"
                    }
                    1 -> {
                        tab.text = "Yellow"
                    }
                    2 -> {
                        tab.text = "Blue"
                    }

                }
            }.attach()
        }
    }

    companion object {
        fun newInstance() = BaseFragment()
    }
}