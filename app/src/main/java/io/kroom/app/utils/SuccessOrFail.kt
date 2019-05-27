package io.kroom.app.views.util

interface SuccessOrFail<R, E> {
    fun onSuccess(s: R)
    fun onFail(f: E)
}