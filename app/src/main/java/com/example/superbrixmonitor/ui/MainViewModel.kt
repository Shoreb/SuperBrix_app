package com.example.superbrixmonitor.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superbrixmonitor.data.ApiService
import com.example.superbrixmonitor.data.EventRequest
import com.example.superbrixmonitor.data.EventResponse
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    var operario by mutableStateOf("OP-001")
    var maquina by mutableStateOf("Fresadora 01")
    var ordenProduccion by mutableStateOf("OP-2458")
    var operacion by mutableStateOf("Fresado")
    var estadoActual by mutableStateOf("Detenido")

    var lastResponse by mutableStateOf<EventResponse?>(null)
    var isLoading by mutableStateOf(false)

    // Reemplazar con la URL desplegada de Google Apps Script
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://script.google.com/macros/s/TU_SCRIPT_ID/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ApiService::class.java)

    fun sendEvent(reporte: String? = null, cat: String? = null, tipo: String? = null, nuevoEstado: String, origen: String = "Botón") {
        viewModelScope.launch {
            isLoading = true
            val request = EventRequest(
                operarioId = operario,
                maquina = maquina,
                ordenProduccion = ordenProduccion,
                operacion = operacion,
                estadoEvento = nuevoEstado,
                reporteOperario = reporte,
                categoria = cat,
                tipoTiempo = tipo,
                origenReporte = origen
            )
            try {
                val res = api.sendEvent(request)
                if (res.isSuccessful) {
                    lastResponse = res.body()
                    estadoActual = nuevoEstado
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}