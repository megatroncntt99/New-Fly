package com.vannv.train.newsfly.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vannv.train.newsfly.data.AllNewsRepository
import com.vannv.train.newsfly.model.remote.RecentNewsResponse
import com.vannv.train.newsfly.network.RequestState
import com.vannv.train.newsfly.network.UiState
import com.vannv.train.newsfly.room.entity.RecentArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 9:30 AM
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val allNewsRepository: AllNewsRepository) :
    ViewModel(), CoroutineScope {

    private val viewModelJob = SupervisorJob()
    private val _searchAllNews = MutableStateFlow(UiState<RecentNewsResponse>())
    val searchAllNews: StateFlow<UiState<RecentNewsResponse>> = _searchAllNews

    private val _uiRecentArticles =
        MutableStateFlow(UiState<PagingData<RecentArticle>>())
     val uiRecentArticles: StateFlow<UiState<PagingData<RecentArticle>>> = _uiRecentArticles

    private val refreshTriggerChannel = Channel<Refresh>()
    private val refreshTrigger = refreshTriggerChannel.receiveAsFlow()

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    private var pendingScrollToTopAfterRefresh = false

    //get recent news
    fun getRecentNews() {
        viewModelScope.launch (coroutineContext){
            allNewsRepository.getRecentNews().cachedIn(viewModelScope)
                .catch { error ->
                    _uiRecentArticles.value = UiState(RequestState.ERROR, message = error.message)
                }.collect {
                    _uiRecentArticles.value = UiState(RequestState.SUCCESS, result = it)
                }
        }
    }

    //get breaking news
    val getPopularNews = refreshTrigger.flatMapLatest { refresh ->
        allNewsRepository.getPopularNews(
            refresh == Refresh.FORCE,
            onFetchSuccess = {
                pendingScrollToTopAfterRefresh = true
            },
            onFetchFailed = { t ->
                viewModelScope.launch { eventChannel.send(Event.ShowErrorMessage(t)) }
            }
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState(RequestState.NON))

    fun onStart() {
        if (getPopularNews.value.state == RequestState.NON) {
            viewModelScope.launch{
                refreshTriggerChannel.send(Refresh.NORMAL)
            }
        }
    }

    fun onManualRefresh() {
        if (getPopularNews.value.state != RequestState.NON) {
            viewModelScope.launch {
                refreshTriggerChannel.send(Refresh.FORCE)
            }
        }
    }

    fun doSearchForNews(q: String) {
        viewModelScope.launch {
            allNewsRepository.searchForNewsItem(q)
                .catch { e ->
                    _searchAllNews.value = UiState(RequestState.ERROR, message = e.toString())
                }
                .collect {
                    _searchAllNews.value = it
                }
        }
    }

    enum class Refresh {
        FORCE, NORMAL
    }

    sealed class Event {
        data class ShowErrorMessage(val error: Throwable) : Event()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}