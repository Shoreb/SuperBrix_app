package com.example.superbrixmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.superbrixmonitor.ui.MainScreen
import com.example.superbrixmonitor.ui.MainViewModel
import com.example.superbrixmonitor.ui.theme.SuperBrixMonitorTheme
import com.example.superbrixmonitor.utils.SpeechRecognizerManager

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var speechManager: SpeechRecognizerManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            speechManager.startListening()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        speechManager = SpeechRecognizerManager(this) { textoDictado ->
            viewModel.reportarNovedad(
                reporte = textoDictado,
                origen = "Voz"
            )
        }

        setContent {
            SuperBrixMonitorTheme {
                MainScreen(
                    viewModel = viewModel,
                    onVoiceClick = { checkAudioPermissionAndListen() }
                )
            }
        }
    }

    private fun checkAudioPermissionAndListen() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED
        ) {
            speechManager.startListening()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}
