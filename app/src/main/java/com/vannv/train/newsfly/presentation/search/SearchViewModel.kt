package com.vannv.train.newsfly.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import com.vannv.train.newsfly.domain.entity.New
import com.vannv.train.newsfly.domain.usecase.SearchUseCase
import com.vannv.train.newsfly.presentation.RequestState
import com.vannv.train.newsfly.presentation.UiState
import com.vannv.train.newsfly.presentation.base.BaseViewModel
import com.vannv.train.newsfly.utils.LogCat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.consumeAsFlow
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 23/05/2022
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) :
    BaseViewModel<SearchRepo>() {
    private val _uiNews = MutableStateFlow(UiState<List<New>>())
    val uiNews: StateFlow<UiState<List<New>>> = _uiNews
    val searchAction = Channel<SearchAction>(Channel.UNLIMITED)
    val keySearch = MutableStateFlow("")

    init {
        handleAction()
        viewModelScope {
            keySearch.collect {
                LogCat.i("Search Key: $it")
                searchListData(it)
            }
        }
    }

    private fun handleAction() {
        viewModelScope {
            searchAction.consumeAsFlow().collect {
                LogCat.i("Search Key: $it")
//                searchListData(it)
            }
        }
    }

    private var searchJob: Job? = null
    private fun searchListData(key: String) {
        if (key.trim().isEmpty()) return
        _uiNews.value = UiState(RequestState.NON
        )
        searchJob?.cancel()
        searchJob = viewModelScope {
            delay(1000L)
            searchUseCase.getNews(repo.repoGetNews(key.lowercase())).collect {
                _uiNews.value = it
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
        searchJob = null
    }
}

sealed class SearchAction {
    data class FetchUser(val key: String) : SearchAction()
}

//sealed class SearchState {
//    object Loading : SearchState()
//    data class Users(val user: List<User>) : SearchState()
//    data class Error(val error: String?) : SearchState()
//}