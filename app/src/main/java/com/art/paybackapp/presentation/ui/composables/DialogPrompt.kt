package com.art.paybackapp.presentation.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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