package rsp.example.kiwitask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import rsp.example.kiwitask.R
import rsp.example.kiwitask.ui.MvRxEpoxyController


abstract class MvRxFragment : BaseMvRxFragment() {

    protected lateinit var recyclerView: EpoxyRecyclerView
    private val epoxyController by lazy { epoxyController() }

    abstract fun epoxyController(): MvRxEpoxyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_base, container, false).apply {
            recyclerView = findViewById<EpoxyRecyclerView>(R.id.recycler_view).apply {
//                (itemAnimator as? androidx.recyclerview.widget.SimpleItemAnimator)?.supportsChangeAnimations = false
                setController(epoxyController)
            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    override fun invalidate() {
        recyclerView.requestModelBuild()
    }

  /*  protected fun showError(error: Throwable) {
        val unknown = resources.getString(R.string.unknown_error)
        val message = if (error is HttpException) {
            error.getErrorMessage() ?: unknown
        } else {
            unknown
        }
        Snackbar.make(fragment_container, message, 4000).apply { show() }
    }*/
}