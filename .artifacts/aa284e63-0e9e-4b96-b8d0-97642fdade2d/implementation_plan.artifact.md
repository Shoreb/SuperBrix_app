# Implementation Plan - SuperBrix Monitor UI Redesign & Backend Action Integration

This implementation plan covers updating the Android app to include a Splash Screen ("SPM AI"), redesigning the main screen to match the exact SuperBrix plant floor mockup, and fully integrating the 3 backend actions (`INICIAR`, `REPORTAR_NOVEDAD`, `CERRAR_Y_CONTINUAR`) from `codigo.gs`.

## User Review Required

> [!IMPORTANT]
> - **Backend Alignment:** The backend (`codigo.gs`) explicitly requires an `accion` parameter (`INICIAR`, `REPORTAR_NOVEDAD`, `CERRAR_Y_CONTINUAR`). We will update `EventRequest` to include `accion`.
> - **Splash Screen:** We will add a professional industrial Splash Screen activity/composable ("SPM AI" - SuperBrix Production Monitor AI) before launching `MainActivity`.
> - **UI Redesign:** The main screen will be redesigned to match the plant floor mockup:
>   - Header: SUPERBRIX · Registro de producción
>   - Context Card: Operario, Máquina, OP (editable)
>   - Primary Actions: ▶ INICIAR TRABAJO, ⏸ REPORTAR PARADA
>   - Input Actions: 🎤 Hablar novedad (Voice-to-Text / SpeechRecognizer), 📝 Escribir novedad (Dialog/Input for typing downtime report)
>   - History & AI Insight viewing.

## Proposed Changes

### Data & Models

#### [MODIFY] [EventModel.kt](file:///C:/Users/Aprendiz/Documents/actividades_android/SuperBrixMonitor/app/src/main/java/com/example/superbrixmonitor/data/EventModel.kt)
- Add `accion` field (`INICIAR`, `REPORTAR_NOVEDAD`, `CERRAR_Y_CONTINUAR`) to `EventRequest`.

### UI & Activities

#### [NEW] [SplashActivity.kt](file:///C:/Users/Aprendiz/Documents/actividades_android/SuperBrixMonitor/app/src/main/java/com/example/superbrixmonitor/SplashActivity.kt)
- Create a modern industrial Splash Activity displaying "SPM AI" and branding, then navigating to `MainActivity`.

#### [MODIFY] [AndroidManifest.xml](file:///C:/Users/Aprendiz/Documents/actividades_android/SuperBrixMonitor/app/src/main/AndroidManifest.xml)
- Set `SplashActivity` as the launcher entry point and register `MainActivity`.

#### [MODIFY] [MainViewModel.kt](file:///C:/Users/Aprendiz/Documents/actividades_android/SuperBrixMonitor/app/src/main/java/com/example/superbrixmonitor/ui/MainViewModel.kt)
- Update functions to pass `accion = "INICIAR"`, `accion = "REPORTAR_NOVEDAD"`, and add support for closing/resolving (`CERRAR_Y_CONTINUAR`).

#### [MODIFY] [MainScreen.kt](file:///C:/Users/Aprendiz/Documents/actividades_android/SuperBrixMonitor/app/src/main/java/com/example/superbrixmonitor/ui/MainScreen.kt)
- Redesign the UI to match the requested mockup with industrial Material 3 styling (Buttons for Start Work, Report Downtime, Voice input, and Text input dialog for typing downtime reports).

## Verification Plan

### Automated Tests
- Build project (`app:assembleDebug`) to verify compilation.

### Manual Verification
- Deploy app, verify Splash Screen ("SPM AI"), verify main screen layout matching mockup, and test backend communication with `INICIAR` and `REPORTAR_NOVEDAD`.
