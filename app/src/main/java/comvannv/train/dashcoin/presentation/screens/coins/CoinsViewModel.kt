package comvannv.train.dashcoin.presentation.screens.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comvannv.train.dashcoin.core.utils.LogCat
import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.domain.model.Coins
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
 * Date: 13/07/2022
 */
@HiltViewModel
class CoinsViewModel @Inject constructor(private val dashCoinUseCase: DashCoinUseCase) : ViewModel() {
    private val _state = MutableStateFlow(UiState<List<Coins>>())
    val state: StateFlow<UiState<List<Coins>>> = _state

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh

    init {
        getCoins()
    }

    private fun getCoins() {
        dashCoinUseCase.getCoins().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.emit(UiState(state = RequestState.SUCCESS, result = result.data ?: emptyList()))
                }
                is Resource.Error -> {
                    _state.emit(
                        UiState(
                            state = RequestState.ERROR,
                            message = result.message ?: "Unexpected Error",
                        )
                    )

                }
                is Resource.Loading -> {
                    _state.emit(
                        UiState(state = RequestState.NON)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefresh.emit(true)
            getCoins()
            _isRefresh.emit(false)
        }
    }

}