package rsp.example.kiwitask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.BaseMvRxFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_base.*
import retrofit2.HttpException
import rsp.example.kiwitask.R
import rsp.example.kiwitask.ui.MvRxEpoxyController
import java.io.IOException


abstract class MvRxFragment : BaseMvRxFragment() {

    private lateinit var recyclerView: EpoxyRecyclerView
    private val epoxyController by lazy { epoxyController() }
    protected open val layoutId = R.layout.fragment_base

    abstract fun epoxyController(): MvRxEpoxyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(layoutId, container, false).apply {
                recyclerView = findViewById<EpoxyRecyclerView>(R.id.recycler_view).apply {
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

    protected fun shoeErrorSnack(error: Throwable) {
       val mes= when(error) {
            is IOException -> R.string.error_connection
            else -> {
                R.string.error_default
            }
        }
        Snackbar.make(fragment_container, mes, 4000).apply { show() }
    }
}