package com.example.superbrixmonitor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(viewModel: MainViewModel, onVoiceClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado industrial
        Text("SUPERBRIX", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
        Text("Registro de Producción", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // Card de información rápida
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F5F9))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Operario: ${viewModel.operario}", fontWeight = FontWeight.SemiBold)
                Text("Máquina: ${viewModel.maquina}")
                Text("Orden Prod: ${viewModel.ordenProduccion}")
                Text("Estado Actual: ${viewModel.estadoActual}", fontWeight = FontWeight.Bold, color = Color(0xFF0D9488))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón Principal: INICIAR PRODUCCIÓN
        Button(
            onClick = { viewModel.sendEvent(nuevoEstado = "Producción Activa", cat = "Producción Activa", tipo = "Productivo") },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("🟢 INICIAR PRODUCCIÓN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Novedad/Parada por VOZ (IA)
        Button(
            onClick = onVoiceClick,
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("🎤 REPORTAR PARADA (VOZ / IA)", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Tiempos Programados / Personales", fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))

        Spacer(modifier = Modifier.height(8.dp))

        // Fila de Botones Rápido (Almuerzo, Descanso, Baño)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(
                onClick = { viewModel.sendEvent(nuevoEstado = "Almuerzo", cat = "Almuerzo", tipo = "Programado") },
                modifier = Modifier.weight(1f).padding(end = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) { Text("🍽 Almuerzo", fontSize = 12.sp) }

            Button(
                onClick = { viewModel.sendEvent(nuevoEstado = "Descanso", cat = "Descanso", tipo = "Programado") },
                modifier = Modifier.weight(1f).padding(horizontal = 2.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7))
            ) { Text("☕ Descanso", fontSize = 12.sp) }

            Button(
                onClick = { viewModel.sendEvent(nuevoEstado = "Baño", cat = "Baño/Necesidad personal", tipo = "Personal") },
                modifier = Modifier.weight(1f).padding(start = 4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF475569))
            ) { Text("🚻 Baño", fontSize = 12.sp) }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mostrar Resultado de IA/Confirmación
        viewModel.lastResponse?.let { resp ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Novedad Registrada (IA)", fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                    Text("Categoría: ${resp.categoria}")
                    Text("Subcausa: ${resp.subcausa}")
                    Text("Tipo Tiempo: ${resp.tipoTiempo}")
                    Text("Confianza IA: ${resp.confianza}")
                }
            }
        }
    }
}