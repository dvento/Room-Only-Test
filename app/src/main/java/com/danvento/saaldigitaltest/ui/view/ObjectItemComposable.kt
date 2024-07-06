package com.danvento.saaldigitaltest.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danvento.saaldigitaltest.domain.model.ObjectItem

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.view
* 
* Created by Dan Vento. 
*/

@Composable
fun ObjectItemComposable(
    objectItem: ObjectItem,
    editEnabled: Boolean = true,
    deleteEnable: Boolean = true,
    onEdit: (id: Int?) -> Unit = {},
    onDelete: (id: Int?) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .clickable {
                onEdit.invoke(objectItem.id)
            },

        ) {
        Column {
            Text(
                text = "${objectItem.type}: ${objectItem.name}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = objectItem.description ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (editEnabled) {
            IconButton(onClick = { onEdit.invoke(objectItem.id) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Object")
            }
        }
        if (deleteEnable) {
            IconButton(onClick = { onDelete.invoke(objectItem.id) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Object")
            }
        }
    }
}

@Composable
@Preview
private fun ObjectItemComposablePreview() {
    val objectItem = ObjectItem(1, "Title", "type", "description")
    ObjectItemComposable(objectItem)
}