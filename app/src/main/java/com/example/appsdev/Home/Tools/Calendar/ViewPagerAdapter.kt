package com.example.appsdev.Home.Tools.Calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (f: Fragment) : FragmentStateAdapter(f) {
    override fun getItemCount(): Int = 10
    override fun createFragment(position: Int): Fragment {
        val frag = NumeroFragment()
        frag.arguments = Bundle().apply {
            putInt("KEYFRA", position)
        }
        return frag
    }
}