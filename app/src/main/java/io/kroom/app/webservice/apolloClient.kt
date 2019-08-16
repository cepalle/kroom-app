package io.kroom.app.webservice

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit

private const val baseUrl = "https://702f57d2.ngrok.io/graphql"
//private const val baseUrl = "http://10.192.2.60:8080/graphql"
private const val subscriptionBaseUrl = "ws://10.192.2.60:8080/graphql"

class GraphClient(private val getToken: () -> String?) {

    private fun interceptor(it: Interceptor.Chain): Response {
        return it.proceed(
            it.request().newBuilder()
                .header(
                    "Kroom-token-id",
                    getToken() ?: ""
                )
                .addHeader(
                    "Kroom-token-id",
                    getToken() ?: ""
                )
                .build()
        )
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(::interceptor)
        .addNetworkInterceptor(::interceptor)
        .build()

    val client: ApolloClient = ApolloClient.builder()
        .serverUrl(baseUrl)
        .okHttpClient(okHttpClient)
        .subscriptionTransportFactory(
            WebSocketSubscriptionTransport.Factory(subscriptionBaseUrl, okHttpClient)
        )
        .subscriptionConnectionParams(mapOf(Pair("Kroom-token-id", getToken() ?: "")))
        .build()
}
