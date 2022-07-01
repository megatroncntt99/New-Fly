package com.vannv.train.newsfly.presentation.livestream.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vannv.train.newsfly.presentation.livestream.live.LiveFragment
import com.vannv.train.newsfly.presentation.livestream.moments.MomentFragment
import com.vannv.train.newsfly.presentation.livestream.recording.RecordingFragment

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class FragmentLiveStreamAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MomentFragment()
            1 -> LiveFragment()
            2 -> RecordingFragment()
            else -> throw IllegalStateException("Unknown Fragment $position")
        }
    }
}