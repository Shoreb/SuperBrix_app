package com.example.superbrixmonitor.data

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EventRepository {
    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(false)
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            if (response.code() == 302) {
                val location = response.header("Location")
                if (location != null) {
                    response.close()
                    val redirectRequest = request.newBuilder()
                        .url(location)
                        .get()
                        .build()
                    return@addInterceptor chain.proceed(redirectRequest)
                }
            }
            response
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://script.google.com/macros/s/AKfycbzhoccKTUiCcDzFIWfmcTmk72PJriDaSzH5gnM6WXBtM0RJNsCAvxOySQioEe911aVI/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    suspend fun sendEvent(request: EventRequest): Response<EventResponse> {
        return api.sendEvent(request)
    }

    suspend fun getEventos(): Response<EventosListResponse> {
        return api.getEventos()
    }
}
