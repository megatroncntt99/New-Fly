package com.vannv.train.newsfly.presentation

import com.vannv.train.newsfly.databinding.FragmentPersonBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.utils.setFullScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: vannv8@fpt.com.vn
 * Date: 06/07/2022
 */

@AndroidEntryPoint
class PersonFragment : BaseFragment<FragmentPersonBinding>() {

    override fun getViewBinding() = FragmentPersonBinding.inflate(layoutInflater)

    override fun setupUI() {
//        requireActivity().window.setFullScreen()
    }

    override fun setupVM() {

    }

}