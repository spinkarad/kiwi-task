package rsp.example.kiwitask.data.entities

/**
 * Created by Radek Špinka on 17.07.2019.
 */
data class ImgUrl(
    val results: List<Result>
){
    val url: String
    get() = results.first().urls.regular
}

data class Result(
    val color: String,
    val id: String,
    val urls: Urls
)

data class Urls(
    val full: String,
    val regular: String,
    val small: String
)