package comvannv.train.dashcoin.presentation.screens.news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.domain.model.FilterNews
import comvannv.train.dashcoin.domain.model.NewsDetail
import comvannv.train.dashcoin.domain.usecase.DashCoinUseCase
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */
@HiltViewModel
class NewsViewModel @Inject constructor(private val dashCoinUseCase: DashCoinUseCase) : ViewModel() {
    private val _stateNews = mutableStateOf(UiState<List<NewsDetail>>())
    val stateNews: State<UiState<List<NewsDetail>>> = _stateNews

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh

    init {
        getNews()
    }

    private fun getNews() {
        dashCoinUseCase.getNews(FilterNews.FILTER_TRENDING).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _stateNews.value = UiState(state = RequestState.ERROR, message = result.message)
                }
                is Resource.Loading -> {
                    _stateNews.value = UiState(state = RequestState.NON)
                }
                is Resource.Success -> {
                    _stateNews.value = UiState(state = RequestState.SUCCESS, result = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefresh.emit(true)
            getNews()
            _isRefresh.emit(true)
        }
    }
}