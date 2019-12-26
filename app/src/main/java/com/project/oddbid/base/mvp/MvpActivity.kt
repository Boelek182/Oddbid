package com.project.oddbid.base.mvp

import android.os.Bundle
import com.project.oddbid.base.ui.BaseActivity
import com.project.oddbid.base.ui.BasePresenter

abstract class MvpActivity<P : BasePresenter<*>> : BaseActivity() {
    private var presenter: P? = null
    protected abstract fun createPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = createPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter?.detachView()
        }
    }
}