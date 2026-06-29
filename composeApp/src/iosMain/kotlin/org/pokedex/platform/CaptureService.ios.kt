package org.pokedex.platform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pokedex.data.model.Pokemon

@Composable
actual fun CaptureScreen(
    pokemon: Pokemon,
    onDismiss: () -> Unit,
    onCaptureComplete: (CaptureData) -> Unit
) {
    var locationText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Capturar ${pokemon.name}") },
        text = {
            Column {
                Text("Onde você capturou ${pokemon.name}?")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = locationText,
                    onValueChange = { locationText = it },
                    placeholder = { Text("Ex: Floresta de Viridian") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (locationText.isNotBlank()) {
                        onCaptureComplete(
                            CaptureData(
                                latitude = 0.0,
                                longitude = 0.0,
                                photoPath = null
                            )
                        )
                    }
                }
            ) { Text("Confirmar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
