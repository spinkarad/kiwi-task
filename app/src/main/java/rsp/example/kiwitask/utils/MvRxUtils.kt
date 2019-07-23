package rsp.example.kiwitask.utils

import com.airbnb.mvrx.*

fun <T> Async<T>?.handle(
    onSuccess: (success: T) -> Unit = {},
    onUninitialized: () -> Unit = {},
    onLoading: (uninitialized: Loading<T>) -> Unit = {},
    onFail: (fail: Fail<T>) -> Unit = {}
) {
    when (this) {
        is Success -> onSuccess(this.invoke())
        is Uninitialized -> onUninitialized()
        is Loading -> onLoading(this)
        is Fail -> onFail(this)
    }
}


fun <T, S: MvRxState> S.onSuccess(asyncVal: Async<T>, stateReducer: S.(T) -> S): S {
    return if (asyncVal is Success && asyncVal() != null) {
        stateReducer(asyncVal())
    } else this
}