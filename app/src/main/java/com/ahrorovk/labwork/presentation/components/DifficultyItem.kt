package com.ahrorovk.labwork.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun DifficultyItem(
    modifier: Modifier = Modifier,
    title: String,
    isChecked: Boolean,
    onClick: (String) -> Unit
) {
    Box(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isChecked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary)
            .clickable { onClick(title) },
        contentAlignment = Alignment.Center
    ) {
        Text(title, Modifier.padding(6.dp))
    }
}