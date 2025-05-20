package com.ahrorovk.labwork.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahrorovk.labwork.data.model.lab_work.LabWork

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LabWorkItem(
    modifier: Modifier = Modifier,
    labWork: LabWork,
    onLongClick: (Int) -> Unit,
    onClick: (Int) -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .combinedClickable(
                onClick = { onClick(labWork.id.toInt()) },
                onLongClick = { onLongClick(labWork.id.toInt()) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(labWork.name, fontSize = 20.sp)
                Text("#${labWork.id}", color = MaterialTheme.colorScheme.secondary)
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Coordinates:")
                Text("x=${labWork.coordinates.x}, y=${labWork.coordinates.y}")
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Points:")
                Text("min=${labWork.minimalPoint.toInt()}, max=${labWork.maximumPoint}")
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Maximum of personal qualities:")
                Text(labWork.personalQualitiesMaximum.toString())
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Discipline:")
                Column(horizontalAlignment = Alignment.End) {
                    Text("name=${labWork.discipline.name}")
                    Text("hours of practice=${labWork.discipline.practiceHours}")
                }
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Difficulty:")
                Text(labWork.difficulty)
            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Created at:")
                Text(labWork.creationDate)
            }
        }
    }
}