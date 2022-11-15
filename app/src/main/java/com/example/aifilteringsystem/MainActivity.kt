package com.example.aifilteringsystem

import android.content.Context
import android.content.Intent
import android.net.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var networkCheck: NetworkConnection

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabs = findViewById<TabLayout>(R.id.main_tab)

        val tabIconArray = arrayOf(
            R.drawable.home_btn,
            R.drawable.save_btn,
            R.drawable.option_btn
        )

        val pager = findViewById<ViewPager2>(R.id.main_pager)
        val adapter = MainPagerAdapter(this, 3)
        pager.adapter = adapter

        // 시작할 때는 첫번째 프래그먼트 지정
        pager.setCurrentItem(0)

        TabLayoutMediator(tabs, pager, { tab, position ->
            tab.icon = getDrawable(tabIconArray[position])
        }).attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        networkCheck = NetworkConnection(this)

        networkCheck.register()

        if(networkCheck.getConnectivityStatus() == null) {
            networkCheck.dialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkCheck.unregister() // 네트워크 객체 해제
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
        when (position) {
            0 -> return AddFragment()
            1 -> return SaveFragment()
            else -> return OptionFragment()
        }
    }


}