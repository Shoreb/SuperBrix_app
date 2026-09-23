package com.example.superbrixmonitor.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    var showTextDialog by remember { mutableStateOf(false) }
    var textoNovedad by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF8FAFC)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("SUPERBRIX", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                    Text("Registro de producción", fontSize = 16.sp, color = Color.Gray)
                }
            }

            // Context Card (Operario, Máquina, OP)
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("👷 Operario: ", fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                            OutlinedTextField(
                                value = viewModel.operario,
                                onValueChange = { viewModel.operario = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🏭 Máquina: ", fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                            OutlinedTextField(
                                value = viewModel.maquina,
                                onValueChange = { viewModel.maquina = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📋 OP: ", fontWeight = FontWeight.Bold, color = Color(0xFF334155))
                            OutlinedTextField(
                                value = viewModel.ordenProduccion,
                                onValueChange = { viewModel.ordenProduccion = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Estado:", fontWeight = FontWeight.SemiBold)
                            Text(viewModel.estadoActual, fontWeight = FontWeight.Bold, color = Color(0xFF0D9488))
                        }
                    }
                }
            }

            // Loading / Error
            if (viewModel.isLoading) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Procesando con SPM AI...", color = Color(0xFF0284C7))
                    }
                }
            }

            viewModel.errorSend?.let { err ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEE2E2))
                    ) {
                        Text(err, color = Color(0xFFDC2626), modifier = Modifier.padding(12.dp), fontWeight = FontWeight.Medium)
                    }
                }
            }

            // ▶ INICIAR TRABAJO
            item {
                Button(
                    onClick = { viewModel.iniciarTrabajo() },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !viewModel.isLoading
                ) {
                    Text("▶ INICIAR TRABAJO", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            // ⏸ REPORTAR PARADA
            item {
                Button(
                    onClick = { viewModel.reportarNovedad("Parada reportada desde botón rápido") },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !viewModel.isLoading
                ) {
                    Text("⏸ REPORTAR PARADA", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            // 🎤 Hablar novedad
            item {
                OutlinedButton(
                    onClick = onVoiceClick,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0284C7)),
                    enabled = !viewModel.isLoading
                ) {
                    Text("🎤 Hablar novedad", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            // 📝 Escribir novedad
            item {
                OutlinedButton(
                    onClick = { showTextDialog = true },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF475569)),
                    enabled = !viewModel.isLoading
                ) {
                    Text("📝 Escribir novedad", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            // AI Response Card
            viewModel.lastResponse?.let { resp ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("⚡ SPM AI - Análisis Operativo", fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                            Text("Acción: ${resp.accion ?: "N/D"}")
                            Text("ID Evento: ${resp.idEvento ?: "N/D"}")
                            if (!resp.categoria.isNullOrEmpty()) {
                                Text("Categoría: ${resp.categoria}")
                                Text("Subcausa: ${resp.subcausa}")
                                Text("Tipo Tiempo: ${resp.tipoTiempo}")
                                Text("Prioridad: ${resp.prioridad}")
                                Text("Confianza IA: ${resp.confianza}")
                            }
                            resp.message?.let { msg ->
                                Text("Mensaje: $msg", fontWeight = FontWeight.SemiBold, color = Color(0xFF1E3A8A))
                            }
                        }
                    }
                }
            }

            // History Button & List
            item {
                Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { viewModel.fetchEventos() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (viewModel.isLoadingEventos) "Cargando..." else "🔄 Consultar Historial")
                    }

                    viewModel.errorEventos?.let { err ->
                        Text(err, color = Color(0xFFDC2626), fontSize = 12.sp)
                    }
                }
            }

            items(viewModel.eventos) { evento ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("${evento.idEvento ?: "ID"} · ${evento.maquina ?: "Máquina"}", fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
                        Text("Estado: ${evento.estadoEvento ?: ""} | OP: ${evento.ordenProduccion ?: ""}")
                        if (!evento.categoriaIA.isNullOrEmpty()) {
                            Text("IA: ${evento.categoriaIA} (${evento.confianzaIA ?: ""})", color = Color(0xFF0D9488), fontSize = 13.sp)
                        }
                        Text("Fecha: ${evento.fecha ?: ""} | Duración: ${evento.duracionMinutos ?: "0"} min", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }

    // Dialog for text input novelty
    if (showTextDialog) {
        AlertDialog(
            onDismissRequest = { showTextDialog = false },
            title = { Text("Escribir Novedad / Parada") },
            text = {
                OutlinedTextField(
                    value = textoNovedad,
                    onValueChange = { textoNovedad = it },
                    placeholder = { Text("Ej. Falta broca de 1/2 pulgada...") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (textoNovedad.isNotBlank()) {
                            viewModel.reportarNovedad(textoNovedad, origen = "Texto")
                            textoNovedad = ""
                            showTextDialog = false
                        }
                    }
                ) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTextDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
