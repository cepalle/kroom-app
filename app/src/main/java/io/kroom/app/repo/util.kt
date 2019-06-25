package io.kroom.app.repo

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CallBackHandler<T>(val res: (Result<Response<T>>) -> Unit) : ApolloCall.Callback<T>() {
    override fun onResponse(response: Response<T>) {
        Log.i("TEST", "onResponse")

        res(success<Response<T>>(response))
    }

    override fun onFailure(e: ApolloException) {
        Log.i("TEST", "onFailure")

        res(failure<Response<T>>(e))
    }
}