package com.kapouter.api.util

interface BasePresenter<in V: BaseView> {

    fun attachView(view: V)

    fun detachView()
}