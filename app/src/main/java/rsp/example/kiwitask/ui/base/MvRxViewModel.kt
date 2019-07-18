package rsp.example.kiwitask.ui.base

import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

/**
 * Created by Radek Špinka on 05.07.2019.
 */
open class MvRxViewModel<S : MvRxState>(initState: S) : BaseMvRxViewModel<S>(initState, debugMode = BuildConfig.DEBUG) {

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }

    protected fun Disposable.autoClear() {
        subscriptions += this
    }
}