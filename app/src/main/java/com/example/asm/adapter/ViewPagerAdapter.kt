package com.example.asm.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()




    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)

    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }


    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}