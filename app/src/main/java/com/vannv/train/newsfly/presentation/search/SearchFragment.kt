package com.vannv.train.newsfly.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
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

    private val args by navArgs<SearchFragmentArgs>()
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
        binding.searchViewModer = viewModel
        binding.lifecycleOwner = this
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

class TestFragment : BaseFragment<FragmentSearchBinding, SearchFragmentArgs, SearchViewModel>() {


    override fun getViewBinding() = FragmentSearchBinding.inflate(layoutInflater)
    override val args: SearchFragmentArgs by navArgs()
    override fun setupUI() {
        args.name
    }

   override val viewModel: SearchViewModel by  viewModels()


}