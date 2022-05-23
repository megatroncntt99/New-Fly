package com.vannv.train.newsfly.presentation.search

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.utils.LogCat
import com.vannv.train.newsfly.utils.Utility
import com.vannv.train.newsfly.utils.handleStateFlow
import com.vannv.train.newsfly.utils.launchWhenCreated
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchFragmentArgs, SearchViewModel>() {

    private val newsAdapter by lazy {
        NewsAdapter {
            println(it.title)
        }
    }
    override val viewModel: SearchViewModel by viewModels()

    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    override val args: SearchFragmentArgs by navArgs()


    override fun setupUI() {
        getVB().rvListNew.adapter = newsAdapter
        getVB().searchViewModer = viewModel
    }

    override fun setupVM() {
        launchWhenCreated {
            viewModel.uiNews.collect {
                handleStateFlow(it,
                    onSuccess = {
                        it.result ?: return@handleStateFlow
                        newsAdapter.submitList(it.result)
                    },
                    onError = {
                        Toast.makeText(requireContext(),it.message ?:" ERROR",Toast.LENGTH_SHORT).show()
                    }

                )
            }
        }
    }
}