package io.kroom.app.repo

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
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

fun <T> flatenResultResponse(r: Result<Response<T>>): Result<T> {
    r.onFailure {
        return failure<T>(it)
    }
    r.onSuccess {
        return if (it.errors().isNotEmpty())
            failure<T>(Throwable(it.errors()[0].message()))
        else {
            val data = it.data()
            if (data != null) success(data)
            else failure(Throwable("Empty Response"))
        }

    }
    return failure<T>(Throwable("unreachable"))
}
