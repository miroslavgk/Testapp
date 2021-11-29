package com.app.pecodetest.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NotifPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa)  {

    private val fragmentsList = arrayListOf<Fragment>()

    fun addFragment(f: Fragment){
        fragmentsList.add(f)
        notifyDataSetChanged()
    }

    fun removeFragment(id: Int){
        fragmentsList.removeAt(id)
    }

    override fun getItemCount(): Int = fragmentsList.size

    override fun createFragment(position: Int): Fragment = fragmentsList[position]
}