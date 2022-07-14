package comvannv.train.dashcoin.presentation.screens.coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import comvannv.train.dashcoin.data.dto.Resource
import comvannv.train.dashcoin.domain.model.Coins
import comvannv.train.dashcoin.domain.usecase.DashCoinUseCase
import comvannv.train.dashcoin.presentation.RequestState
import comvannv.train.dashcoin.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */
@HiltViewModel
class CoinsViewModel @Inject constructor(private val dashCoinUseCase: DashCoinUseCase) : ViewModel() {
    private val _state = MutableStateFlow(UiState<Coins>())
    val state: StateFlow<UiState<Coins>> = _state

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh: StateFlow<Boolean> = _isRefresh

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            dashCoinUseCase.getCoins.invoke().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.emit(UiState(RequestState.SUCCESS, result = result.data))
                    }
                    is Resource.Error -> {
                        _state.emit(
                            UiState(
                                RequestState.ERROR,
                                message = result.message ?: "Unexpected Error"
                            )
                        )

                    }
                    is Resource.Loading -> _state.emit(
                        UiState(
                            RequestState.LOADING,
                        )
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefresh.emit(true)
            getCoins()
            _isRefresh.emit(false)
        }
    }

}