package com.vannv.train.newsfly.presentation.livestream
.live

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentLiveBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.livestream.live.adapter.FragmentLiveAdapter
import com.vannv.train.newsfly.presentation.search.SearchFragmentArgs
import com.vannv.train.newsfly.presentation.search.SearchViewModel
import com.vannv.train.newsfly.utils.Constant
import com.vannv.train.newsfly.utils.DataFragmentResult
import com.vannv.train.newsfly.utils.LogCat
import com.vannv.train.newsfly.utils.transferDataFragmentResultEnt

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
        childFragmentManager.setFragmentResultListener(
            Constant.REQUEST_LIVE_STREAM,
            this,
            fragmentResultListener
        )
    }

    private val fragmentResultListener = FragmentResultListener { _, result ->
        val anyData = result.getParcelable<DataFragmentResult>(Constant.BUNDLE_ANY)
            ?: return@FragmentResultListener
        when (anyData) {
            is DataFragmentResult.OnPanTiltCamera -> {
               transferDataFragmentResultEnt(DataFragmentResult.OnPanTiltCamera)
            }
            is DataFragmentResult.OnNotPanTiltCamera -> {
                transferDataFragmentResultEnt(DataFragmentResult.OnNotPanTiltCamera)
            }
            else -> {
                LogCat.e(anyData.toString())
            }
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


