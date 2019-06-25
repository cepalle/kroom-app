package io.kroom.app.repo

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

class CallBackHandler<T>(val res: (Result<T>) -> Unit) : ApolloCall.Callback<T>() {
    override fun onResponse(response: Response<T>) {
        Log.i("TEST", "onResponse")

        if (response.errors().isNotEmpty()) {
            val data = response.data()
            if (data != null) res(success<T>(data))
            else res(failure<T>(Throwable("Empty Response")))
        } else res(failure<T>(Throwable(response.errors()[0].message())))
    }

    override fun onFailure(e: ApolloException) {
        Log.i("TEST", "onFailure")

        res(failure<T>(e))
    }
}
