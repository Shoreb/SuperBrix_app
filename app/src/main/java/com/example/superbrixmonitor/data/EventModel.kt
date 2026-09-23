package com.example.superbrixmonitor.data
import com.google.gson.annotations.SerializedName

data class EventRequest(
    val accion: String, // "INICIAR", "REPORTAR_NOVEDAD", "CERRAR_Y_CONTINUAR"
    val operarioId: String,
    val maquina: String,
    val ordenProduccion: String,
    val operacion: String,
    val estadoEvento: String? = null,
    val reporteOperario: String? = null,
    val categoria: String? = null,
    val tipoTiempo: String? = null,
    val origenReporte: String = "Botón",
    val accionRealizada: String? = null,
    val responsableAccion: String? = null
)

data class EventResponse(
    val status: String,
    val accion: String?,
    val idEvento: String?,
    val categoria: String?,
    val subcausa: String?,
    val tipoTiempo: String?,
    val prioridad: String?,
    val confianza: String?,
    val message: String?
)

data class EventoResponse(
    @SerializedName("ID_Evento") val idEvento: String?,
    @SerializedName("Fecha") val fecha: String?,
    @SerializedName("Hora_Inicio") val horaInicio: String?,
    @SerializedName("Hora_Fin") val horaFin: String?,
    @SerializedName("Duración_Minutos") val duracionMinutos: String?,
    @SerializedName("Operario_ID") val operarioId: String?,
    @SerializedName("Turno") val turno: String?,
    @SerializedName("Orden_Producción") val ordenProduccion: String?,
    @SerializedName("Operación") val operacion: String?,
    @SerializedName("Máquina") val maquina: String?,
    @SerializedName("Estado_Evento") val estadoEvento: String?,
    @SerializedName("Reporte_Operario") val reporteOperario: String?,
    @SerializedName("Categoría_IA") val categoriaIA: String?,
    @SerializedName("Subcausa") val subcausa: String?,
    @SerializedName("Tipo_Tiempo") val tipoTiempo: String?,
    @SerializedName("Prioridad") val prioridad: String?,
    @SerializedName("Observaciones") val observaciones: String?,
    @SerializedName("Acción_Realizada") val accionRealizada: String?,
    @SerializedName("Responsable_Acción") val responsableAccion: String?,
    @SerializedName("Estado_Solución") val estadoSolucion: String?,
    @SerializedName("Tiempo_Respuesta") val tiempoRespuesta: String?,
    @SerializedName("Origen_Reporte") val origenReporte: String?,
    @SerializedName("Confianza_IA") val confianzaIA: String?,
    @SerializedName("Acción_Sugerida_IA") val accionSugeridaIA: String?,
    @SerializedName("Responsable_Sugerido_IA") val responsableSugeridoIA: String?,
    @SerializedName("Requiere_Detener_Produccion") val requiereDetenerProduccion: String?
)

data class EventosListResponse(
    val status: String,
    val eventos: List<EventoResponse>
)
