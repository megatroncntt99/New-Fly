package com.vannv.train.newsfly.presentation.livestream.live

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.R
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
            selectChipRecord()
        }
        getVB().btnPanTilt.setOnClickListener {
            getVB().viewPagerLive.setCurrentItem(1, false)
            selectChipPanTilt()
        }
        getVB().btnPushToTalk.setOnClickListener {
            getVB().viewPagerLive.setCurrentItem(2, false)
            selectChipTalk()
        }
    }

    private fun selectChipRecord() {
        getVB().btnRecord.setBackgroundResource(R.drawable.bg_select_chip_live)
        getVB().btnPanTilt.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnPushToTalk.setBackgroundResource(R.drawable.bg_unselect_chip)
    }

    private fun selectChipPanTilt() {
        getVB().btnRecord.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnPanTilt.setBackgroundResource(R.drawable.bg_select_chip_live)
        getVB().btnPushToTalk.setBackgroundResource(R.drawable.bg_unselect_chip)
    }

    private fun selectChipTalk() {
        getVB().btnRecord.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnPanTilt.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnPushToTalk.setBackgroundResource(R.drawable.bg_select_chip_live)
    }

    override fun setupVM() {

    }
}