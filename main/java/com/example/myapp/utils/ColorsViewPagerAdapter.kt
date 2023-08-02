package com.example.myapp.utils

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapp.fragments.BlueFragment
import com.example.myapp.fragments.RedFragment
import com.example.myapp.fragments.YellowFragment

class ColorsViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_BLUE_FRAGMENT -> BlueFragment()
            POSITION_RED_FRAGMENT -> RedFragment.newInstance()
            POSITION_YELLOW_FRAGMENT -> YellowFragment()
            else -> error("unsupported")
        }
    }
    companion object {
        const val POSITION_RED_FRAGMENT = 0
        const val POSITION_YELLOW_FRAGMENT = 1
        const val POSITION_BLUE_FRAGMENT = 2

        private const val ITEM_COUNT = 3
    }


}