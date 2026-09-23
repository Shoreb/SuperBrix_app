package com.example.superbrixmonitor.data
import com.example.superbrixmonitor.data.EventRequest
import com.example.superbrixmonitor.data.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET

interface ApiService {
    @POST("exec")
    suspend fun sendEvent(@Body request: EventRequest): Response<EventResponse>

    @GET("exec")
    suspend fun getEventos(): Response<EventosListResponse>
}