package comvannv.train.dashcoin.presentation

/**
 * Author: vannv8@fpt.com.vn
 * Date: 13/07/2022
 */

data class UiState<T>(
    val state: RequestState = RequestState.NON,
    val result: T? = null,
    val message: String? = null,
    val code: Int? = null
)

enum class RequestState(val state: Int) {
    NON(-1), SUCCESS(1), ERROR(0);

    companion object {
        fun find(state: Int): RequestState {
            values().forEach {
                if (it.state == state)
                    return it
            }
            return NON
        }
    }
}