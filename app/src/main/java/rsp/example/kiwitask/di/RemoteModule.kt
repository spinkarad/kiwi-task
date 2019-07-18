package rsp.example.kiwitask.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.schedulers.Schedulers.single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rsp.example.kiwitask.BuildConfig
import rsp.example.kiwitask.data.remote.FlightAdapter
import rsp.example.kiwitask.data.remote.FlightsRemoteService
import rsp.example.kiwitask.data.remote.UnsplashRemoteService

/**
 * Created by Radek Špinka on 14.07.2019.
 */
val remoteModule = module {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BASIC

    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

    val moshi = Moshi.Builder()
        .add(FlightAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()


    single { createWebService<FlightsRemoteService>(BuildConfig.SKY_PICKER_URL, moshi, client) }
    single { createWebService<UnsplashRemoteService>(BuildConfig.UNSPLASH_URL, moshi, client) }
}

inline fun <reified T> createWebService(url: String, moshi: Moshi, client: OkHttpClient): T {


    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
    return retrofit.create(T::class.java)
}