package dev.five_star.boringlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.five_star.boringlist.model.BoringItem

@Composable
fun BoringListItem(name: BoringItem) {
    Box(modifier = Modifier.background(color = MaterialTheme.colors.surface)) {
        Column(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)) {
            Text(text = name.todo, style = MaterialTheme.typography.body1)
            if (name.description.isNotBlank()) {
                Text(
                    text = name.description,
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}