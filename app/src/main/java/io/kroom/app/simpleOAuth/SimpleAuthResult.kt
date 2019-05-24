package io.kroom.app.simpleOAuth

import android.support.annotation.NonNull


class SimpleAuthResult<T> {


    var isSuccess: Boolean = false
        private set
    var contents: T? = null

    var errorMessage: String? = null
    var errorCode: Int = 0

    constructor(isSuccess: Boolean, contents: T) {
        this.isSuccess = isSuccess
        this.contents = contents
    }

    constructor(errorCode: Int, errorMessage: String) {
        this.isSuccess = false
        this.errorCode = errorCode
        this.errorMessage = errorMessage
    }

    companion object {

        fun <T> getSuccessResult(contents: T): SimpleAuthResult<T> {
            return SimpleAuthResult(true, contents)
        }

        fun <T> getFailResult(errorCode: Int, errorMessage: String): SimpleAuthResult<Any> {
            return SimpleAuthResult<Any>(errorCode, errorMessage)
        }
    }
}
