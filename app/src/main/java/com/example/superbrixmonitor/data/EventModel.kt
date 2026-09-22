package com.example.superbrixmonitor.data

data class EventRequest(
    val operarioId: String,
    val maquina: String,
    val ordenProduccion: String,
    val operacion: String,
    val estadoEvento: String,
    val reporteOperario: String? = null,
    val categoria: String? = null,
    val tipoTiempo: String? = null,
    val origenReporte: String = "Botón"
)

data class EventResponse(
    val status: String,
    val idEvento: String?,
    val categoria: String?,
    val subcausa: String?,
    val tipoTiempo: String?,
    val confianza: String?
)