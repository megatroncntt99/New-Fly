package com.vannv.train.newsfly.presentation.search

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vannv.train.newsfly.R
import com.vannv.train.newsfly.databinding.FragmentSearchBinding
import com.vannv.train.newsfly.presentation.base.BaseFragment
import com.vannv.train.newsfly.presentation.main.lightStatusBar
import com.vannv.train.newsfly.presentation.widget.MoveCameraListener
import com.vannv.train.newsfly.presentation.widget.LoadMoreRecyclerView
import com.vannv.train.newsfly.presentation.widget.RepeatListener
import com.vannv.train.newsfly.utils.*
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

        getVB().viewPanTilt.setListener(object : MoveCameraListener {

            override fun noneMoveCamera() {
                getVB().textView.visible()
            }

            override fun moveCameraLeft() {
                getVB().textView.gone()
            }

            override fun moveCameraRight() {
                getVB().textView.gone()
            }

            override fun moveCameraUp() {
                getVB().textView.gone()
            }

            override fun moveCameraDown() {
                getVB().textView.gone()
            }

        })
        getVB().viewPulsator.start()
        getVB().viewPulsator.invisible()
//        getVB().ivMic.setOnTouchListener(RepeatListener(400, 100,
//            onHold = {
//                getVB().viewPulsator.visible()
//            }, onRemoveHold = {
//                getVB().viewPulsator.invisible()
//            })
//        )
        getVB().ivMic.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment2_to_liveStreamFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        lightStatusBar(requireActivity().window,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getVB().viewPulsator.stop()
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