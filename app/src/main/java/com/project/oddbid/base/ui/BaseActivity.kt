package com.project.oddbid.base.ui

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Call

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private var activity: Activity? = null
    private var compositeSubscription: CompositeDisposable? = null
    private var results: List<Call<*>>? = null

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        activity = this
    }

    override fun onDestroy() {
        super.onDestroy()
        onCancelled()
        onUnsubscribe()
    }

    private fun onCancelled() {
        if (results != null) {
            results?.let {
                if (it.isNotEmpty()) {
                    for (call in it) {
                        if (!call.isCanceled) {
                            call.cancel()
                        }
                    }
                }
            }
        }
    }

    private fun onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription?.isDisposed == true) {
            compositeSubscription?.dispose()
        }
    }
}