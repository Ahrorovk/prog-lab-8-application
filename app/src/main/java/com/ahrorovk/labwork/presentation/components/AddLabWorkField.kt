package com.ahrorovk.labwork.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddLabWorkField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$title:")
        CustomTextField(
            value = value,
            modifier = Modifier.fillMaxWidth(0.7f),
            hint = title,
            keyboardType = keyboardType
        ) {
            onValueChange(it)
        }
    }
}

@Preview
@Composable
fun test(){

    AddLabWorkField(
        Modifier,
        "Coordinate X",
        "",
        keyboardType = KeyboardType.Number
    ) {

    }
}