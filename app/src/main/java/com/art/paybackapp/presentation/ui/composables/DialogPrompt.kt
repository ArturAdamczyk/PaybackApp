package com.art.paybackapp.presentation.ui.composables

import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DialogPrompt(
    title: String,
    text: String = "",
    confirmText: String,
    dismissText: String,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    AlertDialog(
        modifier = Modifier
            .width(300.dp),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            if (text.isNotEmpty()) {
                Text(text)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm.invoke()
                }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss.invoke()
                }) {
                Text(dismissText)
            }
        },
        onDismissRequest = {}
    )
}