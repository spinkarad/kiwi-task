package rsp.example.kiwitask.ui.base.flights

import com.airbnb.mvrx.*
import org.koin.android.ext.android.inject
import rsp.example.kiwitask.data.FlightsRepository
import rsp.example.kiwitask.data.entities.Flight
import rsp.example.kiwitask.ui.base.MvRxViewModel

data class FlightsState(
    val flights: Async<List<Flight>> = Uninitialized
) : MvRxState


class FlightsViewModel(initialState: FlightsState, repo: FlightsRepository) :
    MvRxViewModel<FlightsState>(initialState) {


    companion object : MvRxViewModelFactory<FlightsViewModel, FlightsState> {
        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: FlightsState): FlightsViewModel {
            val repo: FlightsRepository by viewModelContext.activity.inject()
            return FlightsViewModel(state, repo)
        }

    }

    init {
        repo.getFlights()
            .doOnError { }
            .execute { copy(flights = it) }
    }
}
