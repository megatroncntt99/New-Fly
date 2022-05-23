package com.vannv.train.newsfly.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.viewbinding.ViewBinding
import com.vannv.train.newsfly.utils.LogCat

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */

abstract class BaseFragment<VB : ViewBinding, Args : NavArgs?, VM : BaseViewModel<*>> : Fragment() {

    @Volatile
    protected var binding: VB? = null
    protected abstract val viewModel: VM
    protected abstract fun getViewBinding(): VB
    protected abstract val args: Args
    protected abstract fun setupUI()
    protected abstract fun setupVM()
    protected fun getVB(): VB = binding ?: synchronized(this) {
        binding = getViewBinding()
        getViewBinding()
    }

    protected val TAG: String = this.javaClass.name


    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogCat.d("Lifecycle in Fragment onAttach $TAG")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCat.d("Lifecycle in Fragment onCreate $TAG")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogCat.d("Lifecycle in Fragment onCreateView $TAG")
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogCat.d("Lifecycle in Fragment onViewCreated $TAG")
        setupUI()
        setupVM()
    }

    override fun onStart() {
        super.onStart()
        LogCat.d("Lifecycle in Fragment onStart $TAG")
    }

    override fun onResume() {
        super.onResume()
        LogCat.d("Lifecycle in Fragment onResume $TAG")
    }

    override fun onPause() {
        super.onPause()
        LogCat.d("Lifecycle in Fragment onPause $TAG")
    }

    override fun onStop() {
        super.onStop()
        LogCat.d("Lifecycle in Fragment onStop $TAG")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        LogCat.d("Lifecycle in Fragment onDestroyView $TAG")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogCat.d("Lifecycle in Fragment onDestroy $TAG")
    }

    override fun onDetach() {
        super.onDetach()
        LogCat.d("Lifecycle in Fragment onDetach $TAG")
    }

}