package com.vannv.train.newsfly.presentation.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vannv.train.newsfly.data.remote.ResultWrapper
import com.vannv.train.newsfly.domain.entity.RecentArticle
import com.vannv.train.newsfly.domain.usecase.SearchUseCase
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Creator: Nguyen Van Van
 * Date: 06,May,2022
 * Time: 3:14 PM
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) :
    BaseViewModel() {
    private val _uiList = MutableStateFlow(UiState<List<RecentArticle>>())
    val uiList: StateFlow<UiState<List<RecentArticle>>> = _uiList

    val channelSearch = Channel<String>(Channel.UNLIMITED)

    var testString = MutableStateFlow("TEST")

    private var searchJob: Job? = null
    fun searchListData(key: String) {
        if (key.trim().isEmpty()) return
        searchJob?.cancel()
        searchJob = viewModelScope {
            delay(1000L)
            searchUseCase.getListData(key.lowercase()).collect {
                _uiList.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
        searchJob = null
    }

}