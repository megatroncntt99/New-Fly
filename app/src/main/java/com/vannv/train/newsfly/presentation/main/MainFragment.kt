package com.vannv.train.newsfly.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentMainBinding
import com.vannv.train.newsfly.presentation.HomeFragment
import com.vannv.train.newsfly.presentation.PersonFragment
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.search.SearchFragment
import java.lang.NullPointerException

/**
 * Author: vannv8@fpt.com.vn
 * Date: 06/07/2022
 */

class MainFragment : BaseFragment<FragmentMainBinding>() {
    private var currentTabId: Int = 0

    override fun getViewBinding() = FragmentMainBinding.inflate(layoutInflater)

    override fun setupUI() {
        getVB().viewPagerMain.isUserInputEnabled = false
        getVB().viewPagerMain.adapter = ViewPagerAdapter(this)
        getVB().bottomNavigation.setOnItemSelectedListener {
            currentTabId = it.itemId
            when (it.itemId) {
                R.id.action_home -> {
                    getVB().viewPagerMain.setCurrentItem(0, false)
                }
                R.id.action_person -> {
                    getVB().viewPagerMain.setCurrentItem(1, false)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun setupVM() {

    }
}

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                PersonFragment()
            }
            else -> throw  NullPointerException("Null Fragment View Pager")
        }
    }

}