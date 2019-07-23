package rsp.example.kiwitask.ui.detail

import android.os.Bundle
import com.airbnb.mvrx.*
import org.koin.android.ext.android.inject
import rsp.example.kiwitask.data.FlightsRepository
import rsp.example.kiwitask.data.entities.FlightWithAllLegs
import rsp.example.kiwitask.ui.base.MvRxViewModel
import rsp.example.kiwitask.utils.onSuccess

data class DetailState(
    var flight: FlightWithAllLegs= FlightWithAllLegs()
) : MvRxState


class DetailViewModel(
    initialState: DetailState,
    flightsRepo: FlightsRepository,
    id: String
) :
    MvRxViewModel<DetailState>(initialState) {

    companion object : MvRxViewModelFactory<DetailViewModel, DetailState> {
        @JvmStatic
        override fun create(
            viewModelContext: ViewModelContext,
            state: DetailState
        ): DetailViewModel {
            val repo: FlightsRepository by viewModelContext.activity.inject()
            val args = DetailFragmentArgs.fromBundle(
                (viewModelContext as FragmentViewModelContext).fragment.arguments ?: Bundle.EMPTY
            )
            return DetailViewModel(state, repo, args.flightId)
        }
    }

    init {
        flightsRepo.getFlightWithAllConnections(id)
            .execute {
                onSuccess(it) { f -> copy(flight = f) }
            }

    }
}
