package com.vannv.train.newsfly.presentation.livestream

import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentLiveStreamBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.livestream.adapter.FragmentLiveStreamAdapter
import com.vannv.train.newsfly.presentation.search.SearchFragmentArgs
import com.vannv.train.newsfly.presentation.search.SearchViewModel

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class LiveStreamFragment : BaseFragment<FragmentLiveStreamBinding, LiveStreamFragmentArgs, SearchViewModel>() {

    private val fragmentAdapter by lazy { FragmentLiveStreamAdapter(this) }

    override val viewModel: SearchViewModel by viewModels()

    override fun getViewBinding() = FragmentLiveStreamBinding.inflate(layoutInflater)

    override val args: LiveStreamFragmentArgs by navArgs()

    override fun setupUI() {
        getVB().viewPagerLiveStream.adapter = fragmentAdapter
        getVB().viewPagerLiveStream.isUserInputEnabled = false
        getVB().btnMoment.setOnClickListener {
            getVB().viewPagerLiveStream.setCurrentItem(0,false)
        }
        getVB().btnLive.setOnClickListener {
            getVB().viewPagerLiveStream.setCurrentItem(1,false)
        }
        getVB().btnRecording.setOnClickListener {
            getVB().viewPagerLiveStream.setCurrentItem(2,false)
        }
    }

    override fun setupVM() {

    }
}