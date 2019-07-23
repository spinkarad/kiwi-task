package rsp.example.kiwitask.ui.flights

import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.fragmentViewModel
import rsp.example.kiwitask.error
import rsp.example.kiwitask.flightItem
import rsp.example.kiwitask.header
import rsp.example.kiwitask.progress
import rsp.example.kiwitask.ui.base.MvRxFragment
import rsp.example.kiwitask.ui.simpleController
import rsp.example.kiwitask.utils.handle

class FlightsFragment : MvRxFragment() {

    private val viewModel: FlightsViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController(viewModel) { state ->

        header { id("header") }

        state.flights.handle(
            onLoading = {
                progress { id("progress") }
            },

            onSuccess = { flights ->
                flights.forEach {
                    it.data.run {

                        flightItem {
                            id("flight_${it.id}")
                            imgUrl(it.locationImgUrl)
                            destination(it.locationName)
                            departureTimestamp(dTime)
                            nights("$nightsInDest")
                            price("$price " + it.currency)
                            listener { _ -> onFlightClick(it.id) }
                        }
                    }
                }
            },
            onFail = {
                error { id("error") }
                shoeErrorSnack(it.error) }
        )
    }

    private fun onFlightClick(flightId: String) {
        findNavController().navigate(FlightsFragmentDirections.flightsToDetail(flightId))
    }
}
