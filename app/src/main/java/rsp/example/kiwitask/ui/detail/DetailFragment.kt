package rsp.example.kiwitask.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.airbnb.epoxy.EpoxyModelGroup
import com.airbnb.mvrx.fragmentViewModel
import com.bumptech.glide.Glide
import rsp.example.kiwitask.*
import rsp.example.kiwitask.ui.base.MvRxFragment
import rsp.example.kiwitask.ui.simpleController

class DetailFragment : MvRxFragment() {


    private val viewModel: DetailViewModel by fragmentViewModel()
    override val layoutId = R.layout.fragment_detail
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.apply {

            viewModel.selectSubscribe(this@DetailFragment, DetailState::flight) {
                val headerImg = findViewById<ImageView>(R.id.detail_header_img)
                Glide.with(context).load(it.flight?.locationImgUrl).centerCrop().into(headerImg)
            }
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->
        val flight = state.flight
        flight.flight?.run {

            flightInfo {
                id("main_info")
                location(locationName)
                timestamp(data.dTime)
                returnTimestamp(flight.returnTimestamp)
                duration(data.flyDuration)
                returnDuration(data.returnDuration)
                from(from)
                returnFrom(flight.returnFrom)
                returnTo(flight.returnTo)
                nights("${data.nightsInDest}")
                price("${data.price} $currency")
                to(to)
            }

            buyButton {
                id("buy_button")
                listener { _ -> onBuyButtonClick(data.deepLink) }
            }

            EpoxyModelGroup(R.layout.route_detail_container,
                flight.route.map {
                    LegInfoBindingModel_()
                        .id("leg_$id")
                        .from(it.from)
                        .isFirstBack(it.id==flight.firstBackId)
                        .isFirstForward(it.id==flight.firstForwardId)
                        .arrivalTimestamp(it.aTime)
                        .departureTimestamp(it.dTime)
                        .to(it.to)
                }
            ).addTo(this@simpleController)
        }
    }

    private fun onBuyButtonClick(link: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(browserIntent)
    }
}
