package com.vannv.train.newsfly.presentation.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentHomeBinding
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.presentation.home.adapter.AllNewsLoadStateAdapter
import com.vannv.train.newsfly.presentation.home.adapter.PopularNewsAdapter
import com.vannv.train.newsfly.presentation.home.adapter.RecentNewsAdapter
import com.vannv.train.newsfly.presentation.main.MainViewModel
import com.vannv.train.newsfly.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Creator: Nguyen Van Van
 * Date: 19,April,2022
 * Time: 3:30 PM
 */
@AndroidEntryPoint
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by viewModels<MainViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private var currentTheme: String = "light"
    private val popularNewsAdapter by lazy {
        PopularNewsAdapter { article ->
            //open article url in browser
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
            startActivity(intent)
        }
    }
    private val recentNewsAdapter by lazy {
        RecentNewsAdapter { article ->
            Log.d("AAAAAAAAA", article.title ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickView()
        initViewModel()
        handleSwipeToRefresh()
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.onStart()
//        homeViewModel.getRecentNews()
    }

    override fun onResume() {
        binding.shimmerLayoutPopular.startShimmer()
        binding.shimmerLayoutRecent.startShimmer()
        super.onResume()
    }

    override fun onPause() {
        binding.shimmerLayoutPopular.stopShimmer()
        binding.shimmerLayoutRecent.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.popularNewsRcv.adapter = popularNewsAdapter
        binding.recentNewsRcv.apply {
            adapter = recentNewsAdapter
            adapter = recentNewsAdapter.withLoadStateHeaderAndFooter(
                header = AllNewsLoadStateAdapter { recentNewsAdapter.retry() },
                footer = AllNewsLoadStateAdapter { recentNewsAdapter.retry() }
            )
        }
    }

    private fun handleSwipeToRefresh() {
        //on swipe
        binding.mainContainer.setOnRefreshListener {
            homeViewModel.onManualRefresh()
            binding.mainContainer.isRefreshing = false
        }

    }

    private fun initViewModel() {
        launchWhenCreated {
            mainViewModel.selectedTheme.collect {
                currentTheme = it
                when (currentTheme) {
                    getString(R.string.light_mode) -> binding.icThemeMode.setImageResource(
                        R.drawable.ic_dark_mode
                    )
                    else -> binding.icThemeMode.setImageResource(R.drawable.ic_light_mode)
                }
            }
        }

        launchWhenCreated {
            homeViewModel.getPopularNews.collect {
                handleStateFlow(it,
                    onSuccess = {
                        popularNewsAdapter.submitList(it.result)
                        //hide shimmer
                        binding.shimmerLayoutPopular.invisible()

                        //show recyclerview
                        binding.popularNewsRcv.visible()

                        //hide error view, if by any means it was shown
                        binding.lytError.invisible()
                    },
                    onError = {
                        showError(it.message ?: "Something went wrong")
                    })
            }
        }
        launchWhenCreated {
            homeViewModel.getData.collect {
                handleStateFlow(it, onSuccess = {
                    it.result ?: return@handleStateFlow
                    when(it.result){
                        GetData.onLoading -> TODO()
                        GetData.onSuccess -> TODO()
                    }
                }, onError ={})
            }
        }
        launchWhenCreated {
            homeViewModel.uiRecentArticles.collect {
                when (it.state) {
                    RequestState.SUCCESS -> {
                        it.result?.let { it1 -> recentNewsAdapter.submitData(it1) }
                        //hide shimmer
                        binding.shimmerLayoutRecent.invisible()
                    }
                    RequestState.ERROR -> showError(it.message ?: "")
                    else -> {}
                }
            }
        }
        launchWhenCreated {
            homeViewModel.events.collect {
                when (it) {
                    is HomeViewModel.Event.ShowErrorMessage -> {
                        showError(it.error.message ?: "Something went wrong")
                    }
                }
            }
        }
    }

    private fun showError(error: String) {

        //stop shimmer effect and don't show it
        binding.shimmerLayoutPopular.stopShimmer()
        binding.shimmerLayoutPopular.invisible()

        //show a snackBar
//        Utility.displayErrorSnackBar(binding.root, error, requireContext())

    }

    private fun initClickView() {
        binding.setOnChangeModeUi {
            if (currentTheme == getString(R.string.light_mode)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                mainViewModel.changeSelectedTheme(getString(R.string.dark_mode))
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                mainViewModel.changeSelectedTheme(getString(R.string.light_mode))
            }
        }
        binding.editText.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

}