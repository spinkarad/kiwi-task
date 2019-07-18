package rsp.example.kiwitask.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import rsp.example.kiwitask.data.entities.ImgUrl

/**
 * Created by Radek Špinka on 17.07.2019.
 */
interface UnsplashRemoteService {

    companion object {
        private const val API_KEY =
            "5a37e7844b4f6c38ea48be62192013a119fe9f36c136c8a694b9b89d0d420563"
    }

    @GET("search/photos/?page=1&per_page=1&orientation=landscape&client_id=$API_KEY")
    fun getLocationImage(@Query("query") query: String): Single<ImgUrl>
}
