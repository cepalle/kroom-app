package io.kroom.app.webservice

import com.apollographql.apollo.ApolloClient
import io.kroom.app.repo.ScharedPreferencesRepo

import okhttp3.Interceptor

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val url = "https:///785dbf4b.ngrok.io/graphql"

private fun tokenInterceptor(builder: Interceptor.Chain): okhttp3.Response {
    return if (ScharedPreferencesRepo.getToken() != null) {
        builder.proceed(
            builder.request().newBuilder().header(
                "Kroom-token-id",
                ScharedPreferencesRepo.getToken()!!
            ).build()
        )
    } else {
        builder.proceed(builder.request())
    }
}

private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .writeTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .addInterceptor(::tokenInterceptor)
    .addNetworkInterceptor {
        it.proceed(
            it.request().newBuilder().header(
                "Authorization",
                "Bearer <TOKEN>"
            ).build()
        )
    }
    .build()

val apolloClient: ApolloClient = ApolloClient.builder()
    .serverUrl(url)
    .okHttpClient(okHttpClient)
    .build()
