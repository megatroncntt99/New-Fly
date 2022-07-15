package com.vannv.train.newsfly.presentation.search

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.widget.LoadMoreRecyclerView
import com.vannv.train.newsfly.utils.handleStateFlow
import com.vannv.train.newsfly.utils.launchWhenCreated
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: vannv8@fpt.com.vn
 * Date: 20/05/2022
 */
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val newsAdapter by lazy {
        NewsAdapter {
            println(it.title)
        }
    }
    private val viewModel: SearchViewModel by viewModels()

    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)

    private val args: SearchFragmentArgs by navArgs()


    override fun setupUI() {
        getVB().searchViewModer = viewModel
        getVB().rvListNew.apply {
            adapter = newsAdapter
            setOnLoadMoreListener(object : LoadMoreRecyclerView.OnLoadMoreListener {
                override fun onShowLoading() {
                    newsAdapter.setWithShowLoadMore(true)
                }

                override fun onLoadMore() {
                    launchWhenCreated {
                        viewModel.searchAction.send(SearchAction.FetchUser("FED"))
                    }
                }
            })
        }
        getVB().edtSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment2_to_mainFragment)
        }
    }

    override fun setupVM() {
        launchWhenCreated {
            viewModel.uiNews.collect {
                handleStateFlow(it,
                    onSuccess = {
                        it.result ?: return@handleStateFlow
                        newsAdapter.submitList(it.result)
                        newsAdapter.setWithShowLoadMore(false)
                        if (it.result.size > 5)
                            getVB().rvListNew.enableLoadMore()
                    },
                    onError = {
                        Toast.makeText(requireContext(), it.message ?: " ERROR", Toast.LENGTH_SHORT)
                            .show()
                    }

                )
            }
        }
    }
}