package com.vannv.train.newsfly.ui.search

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vannv.train.newsfly.databinding.FragmentHomeBinding
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.ui.home.adapter.PopularNewsAdapter
import com.vannv.train.newsfly.ui.home.adapter.RecentNewsAdapter
import com.vannv.train.newsfly.utils.handleStateFlow
import com.vannv.train.newsfly.utils.launchWhenCreated
import dagger.hilt.android.AndroidEntryPoint

/**
 * Creator: Nguyen Van Van
 * Date: 05,May,2022
 * Time: 9:23 AM
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SearchViewModel>()
    private val recentNewsAdapter by lazy {
        SearchListAdapter {
            println(it.content)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            println(text)
            viewModel.searchListData(text.toString())
        }
        binding.rvListNew.adapter = recentNewsAdapter
        launchWhenCreated {
            viewModel.uiList.collect {
                handleStateFlow(it, onSuccess = {
                    println("Số lượng phần tử là: ${it.result?.size}")
                    recentNewsAdapter.submitList(it.result ?: emptyList())
                })
            }
        }
    }
}