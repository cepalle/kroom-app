package io.kroom.app.util

interface SuccessOrFail<R, E> {
    fun onSuccess(s: R)
    fun onFail(f: E)
}