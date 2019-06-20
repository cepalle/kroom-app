package io.kroom.app.utils

interface SuccessOrFail<R, E> {
    fun onSuccess(s: R)
    fun onFail(f: E)
}