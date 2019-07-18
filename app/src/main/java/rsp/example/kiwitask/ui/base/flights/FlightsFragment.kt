package rsp.example.kiwitask.ui.base.flights

import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import rsp.example.kiwitask.flightItem
import rsp.example.kiwitask.header
import rsp.example.kiwitask.progress
import rsp.example.kiwitask.ui.base.MvRxFragment
import rsp.example.kiwitask.ui.simpleController
import rsp.example.kiwitask.utils.handle
import java.text.SimpleDateFormat
import java.util.*

class FlightsFragment : MvRxFragment() {


    private val viewModel: FlightsViewModel by fragmentViewModel()


    override fun epoxyController() = simpleController(viewModel) { state ->

        header { id("header") }

        state.flights.handle(
            onLoading = { progress { id("progress") } },

            onSuccess = { flights ->
                flights.forEach {
                    it.data.run {

                        flightItem {
                            id("flight_${it.id}")
                            imgUrl(it.locationImgUrl)
                            destination("$cityTo, ${countryTo.name}")
                            departureTimestamp(dTime)
                            price("$price " + it.currency)
                            listener(View.OnClickListener { onFlightClick() })
                        }
                    }
                }
            }
        )
    }

    private fun onFlightClick() {

    }
}
