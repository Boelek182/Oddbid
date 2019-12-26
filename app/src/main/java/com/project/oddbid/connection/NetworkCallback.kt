package com.project.oddbid.connection

import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import timber.log.Timber

abstract class NetworkCallback<M> : DisposableObserver<M>() {
    abstract fun onSuccess(model: M)
    abstract fun onFailure(message: String?)
    abstract fun onFinish()

    override fun onComplete() {
        onFinish()
    }

    override fun onNext(t: M) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        Timber.tag("####").d(e.localizedMessage)
        e.printStackTrace()
        if (e is HttpException) {
            when (e.code()) {
                400 -> onFailure("Bad Request")
                401 -> onFailure("Unauthorized")
                403 -> onFailure("Forbidden")
                404 -> onFailure("Not Found")
                413 -> onFailure("Payload Too Large")
                500 -> onFailure("Internal Server Error")
                502 -> onFailure("Bad Gateway")
                503 -> onFailure("Service Unavailable")
                else -> onFailure(e.localizedMessage ?: e.message())
            }
        } else {
            onFailure(e.localizedMessage ?: e.message)
        }
    }
}