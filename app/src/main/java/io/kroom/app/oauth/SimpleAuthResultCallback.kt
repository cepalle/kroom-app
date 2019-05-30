package io.kroom.app.oauth

interface SimpleAuthResultCallback<T> {
    fun onResult(result: SimpleAuthResult<T>)
}

