package com.kapouter.api.util

import io.reactivex.ObservableTransformer

interface BaseSchedulerTransformer<T> : ObservableTransformer<T, T>