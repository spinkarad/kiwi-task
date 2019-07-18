package rsp.example.kiwitask.utils

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Radek Špinka on 15.07.2019.
 */
fun <T> Single<T>.composeSchedulers(): Single<T> = this
    .compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }

fun <T> Observable<T>.composeSchedulers(): Observable<T> = this
    .compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread(), true) }

fun <T> Maybe<T>.composeSchedulers(): Maybe<T> = this
    .compose { it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }

fun <T> Single<List<T>>.switchIfEmptyList(other: Single<List<T>>): Single<List<T>> = this
    .filter { it.isNotEmpty() }
    .switchIfEmpty(other)