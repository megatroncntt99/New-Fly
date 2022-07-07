package com.vannv.train.newsfly.presentation.livestream

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentLiveStreamBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.livestream.adapter.FragmentLiveStreamAdapter
import com.vannv.train.newsfly.presentation.main.lightStatusBar
import com.vannv.train.newsfly.presentation.main.setFullScreen
import com.vannv.train.newsfly.presentation.search.SearchViewModel
import com.vannv.train.newsfly.utils.*

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
            getVB().viewPagerLiveStream.setCurrentItem(0, false)
            selectChipMoment()
            scrollViewCenter(it)
        }
        getVB().btnLive.setOnClickListener {
            getVB().viewPagerLiveStream.setCurrentItem(1, false)
            selectChipLive()
            scrollViewCenter(it)
        }
        getVB().btnRecording.setOnClickListener {
            getVB().viewPagerLiveStream.setCurrentItem(2, false)
            selectChipRecording()
            scrollViewCenter(it)
        }
        childFragmentManager.setFragmentResultListener(
            Constant.REQUEST_LIVE_STREAM,
            this,
            fragmentResultListener
        )
        Handler(Looper.getMainLooper()).postDelayed({
            scrollViewCenter(getVB().btnMoment)
        }, 120L)
        setFullScreen(requireActivity().window,true)
        lightStatusBar(requireActivity().window,false)
    }

    private fun scrollViewCenter(view: View) {
        val scrollX: Int = view.left - getVB().root.width / 2 + view.width / 2
        getVB().tabLayout.smoothScrollTo(scrollX, 0)
    }

    private val fragmentResultListener = FragmentResultListener { _, result ->
        val anyData = result.getParcelable<DataFragmentResult>(Constant.BUNDLE_ANY)
            ?: return@FragmentResultListener
        when (anyData) {
            is DataFragmentResult.OnPanTiltCamera -> {
                getVB().tabLayout.invisible()
            }
            is DataFragmentResult.OnNotPanTiltCamera -> {
                getVB().tabLayout.visible()
            }
            else -> {
                LogCat.e(anyData.toString())
            }
        }
    }

    private fun selectChipMoment() {
        getVB().btnMoment.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipSelect))
        getVB().btnLive.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnRecording.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnMoment.setBackgroundResource(R.drawable.bg_select_chip)
        getVB().btnLive.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnRecording.setBackgroundResource(R.drawable.bg_unselect_chip)
    }

    private fun selectChipLive() {
        getVB().btnMoment.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnLive.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipSelect))
        getVB().btnRecording.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnMoment.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnLive.setBackgroundResource(R.drawable.bg_select_chip)
        getVB().btnRecording.setBackgroundResource(R.drawable.bg_unselect_chip)
    }

    private fun selectChipRecording() {
        getVB().btnMoment.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnLive.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipUnSelect))
        getVB().btnRecording.setTextColor(ContextCompat.getColor(requireContext(), R.color.chipSelect))
        getVB().btnMoment.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnLive.setBackgroundResource(R.drawable.bg_unselect_chip)
        getVB().btnRecording.setBackgroundResource(R.drawable.bg_select_chip)
    }

    override fun setupVM() {

    }
}