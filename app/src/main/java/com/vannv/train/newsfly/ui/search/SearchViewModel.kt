package com.vannv.train.newsfly.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.room.entity.RecentArticle
import com.vannv.train.newsfly.utils.handleStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
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
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {
    private val _uiList = MutableStateFlow(UiState<List<RecentArticle>>())
    val uiList: StateFlow<UiState<List<RecentArticle>>> = _uiList

    private var searchJob: Job? = null
    fun searchListData(key: String) {
        if (key.trim().isEmpty()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            searchRepository.getListSearch(key.lowercase()).collect {
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