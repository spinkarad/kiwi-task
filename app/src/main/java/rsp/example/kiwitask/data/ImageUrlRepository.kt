package rsp.example.kiwitask.data

import io.reactivex.Single
import rsp.example.kiwitask.data.entities.ImgUrl
import rsp.example.kiwitask.data.remote.UnsplashRemoteService
import rsp.example.kiwitask.utils.composeSchedulers

/**
 * Created by Radek Špinka on 17.07.2019.
 */
class ImageUrlRepository(private val remoteService: UnsplashRemoteService) {

    fun getLocationImgUrl(location: String): Single<ImgUrl> {
        return remoteService.getLocationImage("$location city")
            .composeSchedulers()
    }

}