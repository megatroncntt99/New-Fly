package com.vannv.train.newsfly.presentation.livestream.live.pushtotalk

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.databinding.FragmentPushToTalkBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.search.SearchFragmentArgs
import com.vannv.train.newsfly.presentation.search.SearchViewModel

/**
 * Author: vannv8@fpt.com.vn
 * Date: 01/07/2022
 */
class PushToTalkFragment : BaseFragment<FragmentPushToTalkBinding, SearchFragmentArgs, SearchViewModel>() {
    override val viewModel: SearchViewModel
            by viewModels()

    override fun getViewBinding() = FragmentPushToTalkBinding.inflate(layoutInflater)

    override val args: SearchFragmentArgs by navArgs()

    override fun setupUI() {

    }

    override fun setupVM() {

    }
}