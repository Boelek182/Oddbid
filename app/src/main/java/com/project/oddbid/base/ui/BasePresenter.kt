package com.project.oddbid.base.ui

import com.project.oddbid.connection.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import com.project.oddbid.connection.InitRetrofit
import timber.log.Timber

open class BasePresenter<V> {
    var view: V? = null
    protected var apiServices: ApiService? = null
    private var compositeSubscription: CompositeDisposable? = null
    private var subscriber: DisposableObserver<*>? = null

    protected fun attachView(view: V) {
        this.view = view
        apiServices = InitRetrofit.create()?.create(ApiService::class.java)
    }

    fun detachView() {
        view = null
        onUnsubscribe()
    }

    private fun onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription?.isDisposed == true) {
            compositeSubscription?.dispose()
            Timber.tag("TAG").e("onUnsubscribe: ")
        }
    }

    protected fun addSubscribe(observable: Observable<*>, subscriber: DisposableObserver<*>) {
        this.subscriber = subscriber as DisposableObserver<Any>
        if (compositeSubscription == null) {
            compositeSubscription = CompositeDisposable()
        }
        val observables = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber)
        compositeSubscription?.add(observables)
    }

    fun stop() {
        if (subscriber != null) {
            subscriber?.onComplete()
        }
    }
}