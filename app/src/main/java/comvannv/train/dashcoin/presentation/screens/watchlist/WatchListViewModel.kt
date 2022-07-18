package comvannv.train.dashcoin.presentation.screens.watchlist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comvannv.train.dashcoin.domain.model.CoinById
import comvannv.train.dashcoin.domain.usecase.DashCoinUseCase
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 15/07/2022
 */
@HiltViewModel
class WatchListViewModel @Inject constructor(private val dashCoinUseCase: DashCoinUseCase) : ViewModel() {
    private val _watchListState = mutableStateOf(UiState<List<CoinById>>())
    val watchListState: State<UiState<List<CoinById>>> = _watchListState

    private var recentLyDeleteCoin: CoinById? = null
    private var getNotesJob: Job? = null

    init {
        getAllCoins()
    }

    fun onEvent(event: WatchListEvent) {
        when (event) {
            is WatchListEvent.AddCoin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dashCoinUseCase.addCoin(event.coin)
                }
            }
            is WatchListEvent.DeleteCoin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dashCoinUseCase.deleteCoin(event.coin)
                    recentLyDeleteCoin = event.coin
                }
            }
            is WatchListEvent.RestoreDeleteCoin -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dashCoinUseCase.addCoin(recentLyDeleteCoin ?: return@launch)
                    recentLyDeleteCoin = null
                }
            }
        }
    }

    private fun getAllCoins() {
        getNotesJob?.cancel()
        getNotesJob = dashCoinUseCase.getAllCoins.invoke().onEach {
            _watchListState.value = UiState(RequestState.SUCCESS, result = it)

        }.launchIn(viewModelScope)
    }
}

sealed class WatchListEvent {
    data class DeleteCoin(val coin: CoinById) : WatchListEvent()
    data class AddCoin(val coin: CoinById) : WatchListEvent()
    object RestoreDeleteCoin : WatchListEvent()
}