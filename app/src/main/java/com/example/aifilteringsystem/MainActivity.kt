package com.example.aifilteringsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pager = findViewById<ViewPager2>(R.id.main_pager)
        pager.adapter = MainPagerAdapter(this, 1)
    }
}


class MainPagerAdapter(
    fragmentActivity: FragmentActivity,
    val tabCount: Int,
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return MainFragment()
        /*
        when (position) {
            0 -> return InstaFeedFragment()
            1 -> return instaPostFragment
            else -> return InstaProfileFragment()
        }*/
    }
}