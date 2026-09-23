package com.example.superbrixmonitor.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superbrixmonitor.data.EventRepository
import com.example.superbrixmonitor.data.EventRequest
import com.example.superbrixmonitor.data.EventResponse
import com.example.superbrixmonitor.data.EventoResponse
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var operario by mutableStateOf("Juan Pérez")
    var maquina by mutableStateOf("Fresadora 03")
    var ordenProduccion by mutableStateOf("4587")
    var operacion by mutableStateOf("Mecanizado")
    var turno by mutableStateOf("Turno 1")
    var estadoActual by mutableStateOf("En producción")

    var lastResponse by mutableStateOf<EventResponse?>(null)
    var isLoading by mutableStateOf(false)
    var errorSend by mutableStateOf<String?>(null)

    private val repository = EventRepository()

    fun iniciarTrabajo() {
        viewModelScope.launch {
            isLoading = true
            errorSend = null
            val request = EventRequest(
                accion = "INICIAR",
                operarioId = operario,
                maquina = maquina,
                ordenProduccion = ordenProduccion,
                operacion = operacion
            )
            try {
                val res = repository.sendEvent(request)
                if (res.isSuccessful) {
                    lastResponse = res.body()
                    estadoActual = "En producción"
                } else {
                    errorSend = "Error al iniciar trabajo (${res.code()})"
                }
            } catch (e: Exception) {
                errorSend = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun reportarNovedad(reporte: String, origen: String = "Botón") {
        viewModelScope.launch {
            isLoading = true
            errorSend = null
            val request = EventRequest(
                accion = "REPORTAR_NOVEDAD",
                operarioId = operario,
                maquina = maquina,
                ordenProduccion = ordenProduccion,
                operacion = operacion,
                reporteOperario = reporte,
                origenReporte = origen
            )
            try {
                val res = repository.sendEvent(request)
                if (res.isSuccessful) {
                    lastResponse = res.body()
                    estadoActual = "Novedad / Parada"
                } else {
                    errorSend = "Error al reportar novedad (${res.code()})"
                }
            } catch (e: Exception) {
                errorSend = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun cerrarYContinuar(accionRealizada: String? = null, responsableAccion: String? = null) {
        viewModelScope.launch {
            isLoading = true
            errorSend = null
            val request = EventRequest(
                accion = "CERRAR_Y_CONTINUAR",
                operarioId = operario,
                maquina = maquina,
                ordenProduccion = ordenProduccion,
                operacion = operacion,
                accionRealizada = accionRealizada,
                responsableAccion = responsableAccion
            )
            try {
                val res = repository.sendEvent(request)
                if (res.isSuccessful) {
                    lastResponse = res.body()
                    estadoActual = "En producción"
                    fetchEventos()
                } else {
                    errorSend = "Error al cerrar novedad (${res.code()})"
                }
            } catch (e: Exception) {
                errorSend = "Error de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    var eventos by mutableStateOf<List<EventoResponse>>(emptyList())
    var isLoadingEventos by mutableStateOf(false)
    var errorEventos by mutableStateOf<String?>(null)

    fun fetchEventos() {
        viewModelScope.launch {
            isLoadingEventos = true
            errorEventos = null
            try {
                val res = repository.getEventos()
                if (res.isSuccessful) {
                    eventos = res.body()?.eventos ?: emptyList()
                } else {
                    errorEventos = "Error del servidor: ${res.code()}"
                }
            } catch (e: Exception) {
                errorEventos = "Error de conexión: ${e.message}"
            } finally {
                isLoadingEventos = false
            }
        }
    }
}
