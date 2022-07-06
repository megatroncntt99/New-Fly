package com.vannv.train.newsfly.presentation

import com.vannv.train.newsfly.databinding.FragmentHomeBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: vannv8@fpt.com.vn
 * Date: 06/07/2022
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun setupUI() {

    }

    override fun setupVM() {

    }
}