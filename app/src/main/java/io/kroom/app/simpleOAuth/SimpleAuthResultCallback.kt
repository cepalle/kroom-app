package io.kroom.app.simpleOAuth

interface SimpleAuthResultCallback<T> {
    fun onResult(result: SimpleAuthResult<T>)
}

