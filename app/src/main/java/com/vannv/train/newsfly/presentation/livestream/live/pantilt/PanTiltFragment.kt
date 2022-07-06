package com.vannv.train.newsfly.presentation.livestream.live.pantilt

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.databinding.FragmentPanTiltBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.search.SearchFragmentArgs
import com.vannv.train.newsfly.presentation.search.SearchViewModel
import com.vannv.train.newsfly.presentation.widget.MoveCameraListener
import com.vannv.train.newsfly.utils.DataFragmentResult
import com.vannv.train.newsfly.utils.transferDataFragmentResultEnt

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class PanTiltFragment : BaseFragment<FragmentPanTiltBinding, SearchFragmentArgs, SearchViewModel>() {
    override val viewModel: SearchViewModel
            by viewModels()

    override fun getViewBinding() = FragmentPanTiltBinding.inflate(layoutInflater)

    override val args: SearchFragmentArgs by navArgs()

    override fun setupUI() {
        getVB().panTiltCamera.setListener(object : MoveCameraListener {
            override fun noneMoveCamera() {
                transferDataFragmentResultEnt(DataFragmentResult.OnNotPanTiltCamera)
            }

            override fun moveCameraLeft() {
                transferDataFragmentResultEnt(DataFragmentResult.OnPanTiltCamera)
            }

            override fun moveCameraRight() {
                transferDataFragmentResultEnt(DataFragmentResult.OnPanTiltCamera)
            }

            override fun moveCameraUp() {
                transferDataFragmentResultEnt(DataFragmentResult.OnPanTiltCamera)
            }

            override fun moveCameraDown() {
                transferDataFragmentResultEnt(DataFragmentResult.OnPanTiltCamera)
            }

        })
    }

    override fun setupVM() {

    }
}