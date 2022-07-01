package com.vannv.train.newsfly.presentation.livestream.live

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.databinding.FragmentLiveBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.livestream.live.adapter.FragmentLiveAdapter
import com.vannv.train.newsfly.presentation.search.SearchFragmentArgs
import com.vannv.train.newsfly.presentation.search.SearchViewModel

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class LiveFragment : BaseFragment<FragmentLiveBinding, SearchFragmentArgs, SearchViewModel>() {

    private val fragmentLiveAdapter by lazy { FragmentLiveAdapter(this) }

    override val viewModel: SearchViewModel by viewModels()

    override fun getViewBinding() = FragmentLiveBinding.inflate(layoutInflater)

    override val args: SearchFragmentArgs by navArgs()

    override fun setupUI() {
        getVB().viewPagerLive.adapter = fragmentLiveAdapter
        getVB().viewPagerLive.isUserInputEnabled = false
        getVB().btnRecord.setOnClickListener {
            getVB().viewPagerLive.setCurrentItem(0, false)
        }
        getVB().btnPanTilt.setOnClickListener {
            getVB().viewPagerLive.setCurrentItem(1, false)
        }
        getVB().btnPushToTalk.setOnClickListener {
            getVB().viewPagerLive.setCurrentItem(2, false)
        }
    }

    override fun setupVM() {

    }
}