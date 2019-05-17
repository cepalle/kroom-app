package io.kroom.app.client

import android.support.annotation.UiThread
import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import io.kroom.app.graphql.TrackQuery
import okhttp3.OkHttpClient


object KroomClient {
    private const val baseUrl = "https://407df380.ngrok.io/graphql"
    private val okHttpClient = OkHttpClient.Builder().build()
    private val apolloClient = ApolloClient.builder()
        .serverUrl(baseUrl)
        .okHttpClient(okHttpClient)
        .build()

    @UiThread
    fun trackById(id: Int) {
        apolloClient.query(
            TrackQuery.builder().id(3135556).build()
        ).enqueue(object : ApolloCall.Callback<TrackQuery.Data>() {
            override fun onResponse(response: Response<TrackQuery.Data>) {
                Log.println(Log.INFO, "test", response.data()!!.track().toString())
            }

            override fun onFailure(e: ApolloException) {
                Log.println(Log.INFO, "test", e.message)
            }
        })
    }
}