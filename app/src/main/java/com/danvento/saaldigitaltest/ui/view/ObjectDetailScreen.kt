package com.danvento.saaldigitaltest.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danvento.saaldigitaltest.MainActivity
import com.danvento.saaldigitaltest.R
import com.danvento.saaldigitaltest.core.utils.DIPreviewWrapper
import com.danvento.saaldigitaltest.domain.model.ObjectItem
import com.danvento.saaldigitaltest.ui.model.UIState
import com.danvento.saaldigitaltest.ui.viemodel.ObjectDetailViewModel
import org.koin.compose.koinInject

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.view
* 
* Created by Dan Vento. 
*/

@Composable
fun ObjectDetailScreen(
    navController: NavController,
    objectId: Int?,
    onFabAction: (action: (() -> Unit)?, show: Boolean) -> Unit
) {
    val viewModel = koinInject<ObjectDetailViewModel>()

    LaunchedEffect(objectId) {
        if (objectId == null) {
            // Hide FAB when creating a new object
            onFabAction(null, false)
            viewModel.initializeNewObject()

        } else {
            // Show FAB when editing an existing object to allow creating a new one
            onFabAction({
                // TODO: SHOW MESSAGE
                navController.navigate(MainActivity.DETAIL_COMPOSABLE)
            }, true)
            viewModel.getObjectById(objectId)
        }
    }

    ObjectDetailMainContent(viewModel, navController, objectId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ObjectDetailMainContent(
    viewModel: ObjectDetailViewModel,
    navController: NavController,
    objectId: Int?
) {
    val objectItemState by viewModel.objectItemState.collectAsState()
    val objectListState by viewModel.objectListState.collectAsState()
    val relationState by viewModel.relationState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (objectId == null) stringResource(id = R.string.create_object) else stringResource(
                            id = R.string.edit_object
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(18.dp)
        ) {
            when (val objectItem = objectItemState) {
                is UIState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is UIState.Success -> {
                    val data = objectItem.data

                    var objectType by remember { mutableStateOf(data.type) }
                    var objectTitle by remember { mutableStateOf(data.name) }
                    var objectDescription by remember {
                        mutableStateOf(
                            data.description ?: ""
                        )
                    }

                    // Relations
                    val objectRelations = remember(data.relations) {
                        mutableStateMapOf<Int, ObjectItem>().apply {
                            data.relations?.let { relations ->
                                putAll(relations)
                            }
                        }
                    }

                    val nonRelatedObjects = if (objectListState is UIState.Success) {
                        (objectListState as UIState.Success<List<ObjectItem>>).data.filter { objInAll -> objInAll.id !in objectRelations.values.map { it.id } }
                    } else {
                        emptyList()
                    }
                    var showDialog by remember { mutableStateOf(false) }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        TextField(
                            value = objectType,
                            onValueChange = { newValue -> objectType = newValue },
                            label = { Text(stringResource(id = R.string.type_label)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = objectTitle,
                            onValueChange = { newValue -> objectTitle = newValue },
                            label = { Text(stringResource(id = R.string.title_label)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = objectDescription,
                            onValueChange = { newValue -> objectDescription = newValue },
                            label = { Text(stringResource(id = R.string.description_label)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        if (objectId != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = stringResource(id = R.string.related_objects),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {
                                    showDialog = true
                                }) {
                                    Icon(Icons.Default.AddCircle, contentDescription = "Edit Object")
                                }
                            }
                        }

                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(objectRelations.values.toList()) { childObj ->
                                ObjectItemComposable(objectItem = childObj, editEnabled = false, onDelete = {
                                   /* objectRelations.remove(it)
                                    viewModel.deleteRelation(it!!)*/
                                })
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            if (objectId != null) {
                                Button(
                                    onClick = {
                                        viewModel.deleteObject(
                                            objectId,
                                            objectType,
                                            objectTitle,
                                            objectDescription
                                        )
                                        navController.popBackStack()
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Text(stringResource(id = R.string.delete))
                                }
                            }
                            Button(
                                onClick = {
                                    if (objectId == null) {
                                        viewModel.createObject(
                                            objectType,
                                            objectTitle,
                                            objectDescription
                                        )
                                    } else {
                                        viewModel.updateObject(
                                            objectId,
                                            objectType,
                                            objectTitle,
                                            objectDescription
                                        )
                                    }
                                    navController.popBackStack()
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(stringResource(id = R.string.save))
                            }
                        }
                        if (showDialog) {
                            ObjectSelectionDialog(
                                objects = nonRelatedObjects,
                                onDismissRequest = { showDialog = false },
                                onObjectSelected = { selectedObject ->
                                    viewModel.addRelation(objectId!!, selectedObject.id!!)
                                    showDialog = false
                                }
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    Text(
                        text = stringResource(
                            id = R.string.object_operation_error,
                            objectItem.message ?: stringResource(id = R.string.unknown_error)
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ObjectDetailViewPreview() {
    DIPreviewWrapper {
        val mockedObject = ObjectItem(
            1,
            "Title",
            "type",
            "description",
            mapOf(
                1 to ObjectItem(2, "Title2", "type", "description"),
                2 to ObjectItem(3, "Title3", "type", "description")
            )
        )
        val viewModel = koinInject<ObjectDetailViewModel>()
        val navController = rememberNavController()
        ObjectDetailMainContent(viewModel, navController, null)
    }
}