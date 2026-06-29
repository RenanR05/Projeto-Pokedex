package org.pokedex.platform

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.pokedex.data.model.Pokemon
import java.io.File

private val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

private fun checkPermissions(context: Context): Boolean {
    val camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    val location = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    return camera && location
}

@SuppressLint("MissingPermission")
private fun getLastLocation(context: Context): Location? {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return runCatching {
        lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            ?: lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            ?: lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
    }.getOrNull()
}

@Composable
actual fun CaptureScreen(
    pokemon: Pokemon,
    onDismiss: () -> Unit,
    onCaptureComplete: (CaptureData) -> Unit
) {
    val context = LocalContext.current
    var allGranted by remember { mutableStateOf(false) }
    var permissionsDenied by remember { mutableStateOf(false) }
    var pendingLaunch by remember { mutableStateOf(false) }
    var currentFile by remember { mutableStateOf<File?>(null) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        allGranted = checkPermissions(context)
    }

    val permissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val cameraGranted = results[Manifest.permission.CAMERA] == true
        val locationGranted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        allGranted = cameraGranted && locationGranted
        if (!allGranted) {
            permissionsDenied = true
            pendingLaunch = false
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            val filePath = currentFile?.absolutePath?.let { "file://$it" }
            val location = getLastLocation(context)
            onCaptureComplete(
                CaptureData(
                    latitude = location?.latitude ?: 0.0,
                    longitude = location?.longitude ?: 0.0,
                    photoPath = filePath
                )
            )
        } else {
            onDismiss()
        }
    }

    LaunchedEffect(allGranted, pendingLaunch) {
        if (allGranted && pendingLaunch) {
            pendingLaunch = false
            val file = withContext(Dispatchers.IO) {
                File.createTempFile("pokemon_${pokemon.id}_", ".jpg", context.externalCacheDir)
            }
            currentFile = file
            photoUri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
            cameraLauncher.launch(photoUri!!)
        }
    }

    if (permissionsDenied) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Permissão Negada") },
            text = { Text("Câmera e localização são necessárias para capturar o Pokémon. Por favor, conceda as permissões.") },
            confirmButton = {
                TextButton(onClick = {
                    permissionsDenied = false
                    pendingLaunch = true
                    permissionsLauncher.launch(REQUIRED_PERMISSIONS)
                }) { Text("Tentar Novamente") }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancelar") }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Capturar ${pokemon.name}") },
            text = {
                Column {
                    Text("Tire uma foto para registrar a captura!")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Sua localização GPS será salva automaticamente.")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    pendingLaunch = true
                    if (!allGranted) {
                        permissionsLauncher.launch(REQUIRED_PERMISSIONS)
                    }
                }) {
                    Text("Abrir Câmera")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text("Cancelar") }
            }
        )
    }
}
