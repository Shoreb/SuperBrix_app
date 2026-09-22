package com.example.superbrixmonitor.data
import com.example.superbrixmonitor.data.EventRequest
import com.example.superbrixmonitor.data.EventResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("exec")
    suspend fun sendEvent(@Body request: EventRequest): Response<EventResponse>
}