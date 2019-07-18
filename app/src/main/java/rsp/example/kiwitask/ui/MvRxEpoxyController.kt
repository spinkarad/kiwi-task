package rsp.example.kiwitask.ui

import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.withState
import rsp.example.kiwitask.ui.base.MvRxFragment
import rsp.example.kiwitask.ui.base.MvRxViewModel

/**
 * For use with [BaseFragment.epoxyController].
 *
 * This builds Epoxy models in a background thread.
 */
@Suppress("KDocUnresolvedReference")
open class MvRxEpoxyController(val buildModelsCallback: EpoxyController.() -> Unit = {}) : AsyncEpoxyController() {

    override fun buildModels() {
        buildModelsCallback()
    }

    override fun moveModel(fromPosition: Int, toPosition: Int) {
        // Do nothing to prevent change data position by dragging -> it have to be handled manually by setting state
    }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodel will be provided.
 */
fun <S : MvRxState, A : MvRxViewModel<S>> MvRxFragment.simpleController(
    viewModel: A,
    buildModels: EpoxyController.(state: S) -> Unit
) = MvRxEpoxyController {
    if (view == null || isRemoving) return@MvRxEpoxyController
    withState(viewModel) { state ->
        buildModels(state)
    }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodels will be provided.
 */
fun <A : BaseMvRxViewModel<B>, B : MvRxState, C : BaseMvRxViewModel<D>, D : MvRxState> MvRxFragment.simpleController(
    viewModel1: A,
    viewModel2: C,
    buildModels: EpoxyController.(state1: B, state2: D) -> Unit
) = MvRxEpoxyController {
    if (view == null || isRemoving) return@MvRxEpoxyController
    withState(viewModel1, viewModel2) { state1, state2 ->
        buildModels(state1, state2)
    }
}

/**
 * Create a [MvRxEpoxyController] that builds models with the given callback.
 * When models are built the current state of the viewmodels will be provided.
 */
fun <A : BaseMvRxViewModel<B>, B : MvRxState, C : BaseMvRxViewModel<D>, D : MvRxState, E : BaseMvRxViewModel<F>, F : MvRxState> MvRxFragment.simpleController(
    viewModel1: A,
    viewModel2: C,
    viewModel3: E,
    buildModels: EpoxyController.(state1: B, state2: D, state3: F) -> Unit
) = MvRxEpoxyController {
    if (view == null || isRemoving) return@MvRxEpoxyController
    withState(viewModel1, viewModel2, viewModel3) { state1, state2, state3 ->
        buildModels(state1, state2, state3)
    }
}