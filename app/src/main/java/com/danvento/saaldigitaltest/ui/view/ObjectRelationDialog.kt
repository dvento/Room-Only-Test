package com.danvento.saaldigitaltest.ui.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.danvento.saaldigitaltest.R
import com.danvento.saaldigitaltest.domain.model.ObjectItem

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.view
* 
* Created by Dan Vento. 
*/

@Composable
fun ObjectSelectionDialog(
    objects: List<ObjectItem>,
    onDismissRequest: () -> Unit,
    onObjectSelected: (ObjectItem) -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {

        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(stringResource(id = R.string.cancel))
            }
        },
        text = {
            LazyColumn {
                items(objects) { obj ->
                    ObjectItemComposable(objectItem = obj,
                        editEnabled = false,
                        deleteEnable = false,
                        onEdit = {
                            onObjectSelected(obj)
                            onDismissRequest()
                        })

                }
            }
        }
    )
}

@Composable
@Preview
private fun PreviewObjectSelectionDialog() {

    val mockedObject = ObjectItem(
        1,
        "Title",
        "type",
        "description"
    )

    val mockedList = listOf(mockedObject)

    ObjectSelectionDialog(
        objects = mockedList,
        onDismissRequest = {},
        onObjectSelected = {}
    )
}