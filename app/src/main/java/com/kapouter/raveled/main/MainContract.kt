package com.kapouter.raveled.main

import com.kapouter.api.util.BasePresenter
import com.kapouter.api.util.BaseView

object MainContract {

    interface View: BaseView {

    }

    interface Presenter: BasePresenter<View> {

    }
}