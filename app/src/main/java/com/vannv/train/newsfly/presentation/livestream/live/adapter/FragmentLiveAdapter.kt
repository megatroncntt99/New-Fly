package com.vannv.train.newsfly.presentation.livestream.live.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vannv.train.newsfly.presentation.livestream.live.pantilt.PanTiltFragment
import com.vannv.train.newsfly.presentation.livestream.live.pushtotalk.PushToTalkFragment
import com.vannv.train.newsfly.presentation.livestream.live.record.RecordFragment

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class FragmentLiveAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecordFragment()
            1 -> PanTiltFragment()
            2 -> PushToTalkFragment()
            else -> throw NullPointerException("Not found Fragment with position $position")
        }
    }
}