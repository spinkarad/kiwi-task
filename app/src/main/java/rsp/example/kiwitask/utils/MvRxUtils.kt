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