package com.vannv.train.newsfly.presentation.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vannv.train.newsfly.data.remote.base.Repo
import com.vannv.train.newsfly.data.remote.base.ResultWrapper
import com.vannv.train.newsfly.data.remote.repo.BaseRepo
import com.vannv.train.newsfly.data.remote.repo.RepoManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

/**
 * Author: vannv8@fpt.com.vn
 * Date: 18/05/2022
 */

abstract class BaseViewModel<R : BaseRepo> : ViewModel(), LifecycleObserver, CoroutineScope {

    var _error = MutableStateFlow(ResultWrapper.Error("-1"))
    var error: StateFlow<ResultWrapper.Error> = _error
    var baseError: StateFlow<ResultWrapper.Error> = _error
    protected var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val netWorkError = MutableLiveData<Boolean>()
    val viewModelJob = SupervisorJob()
    protected val repo = RepoManager() as R

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _error.value =
            ResultWrapper.Error("Error by ${exception.message.toString()} on ${Thread.currentThread().name}")
    }

    fun CoroutineScope.viewModelScope(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch(
            coroutineContext + coroutineExceptionHandler + CoroutineName(
                getMethodName()
            )
        ) { block(this) }
    }

    fun viewModelScope(
        coroutineContext: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return CoroutineScope(
            coroutineContext + coroutineExceptionHandler + viewModelJob + CoroutineName(
                getMethodName()
            )
        )
            .launch { block(this) }
    }

    private fun getMethodName() = Thread.currentThread().stackTrace[5].methodName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}