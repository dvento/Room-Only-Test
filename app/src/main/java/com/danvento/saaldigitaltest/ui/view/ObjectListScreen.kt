package com.danvento.saaldigitaltest.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.danvento.saaldigitaltest.ui.viemodel.ObjectListViewModel
import org.koin.compose.koinInject

/*
* Saal Digital Test:
* com.danvento.saaldigitaltest.ui.view
* 
* Created by Dan Vento. 
*/

@Composable
fun ObjectListScreen(
    navController: NavController,
    onFabAction: (action: (() -> Unit)?, show: Boolean) -> Unit,
) {
    val viewModel = koinInject<ObjectListViewModel>()

    LaunchedEffect(Unit) {
        onFabAction({
            navController.navigate(MainActivity.DETAIL_COMPOSABLE)
        }, true)
    }


    ObjectListMainView(viewModel = viewModel, navController = navController)

}

@Composable
private fun ObjectListMainView(viewModel: ObjectListViewModel, navController: NavController) {

    val objectList: UIState<List<ObjectItem>> by viewModel.objectListFlow.collectAsState(initial = UIState.Loading)

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.objects_list_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            when (val result = objectList) {
                is UIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UIState.Success -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(result.data) { obj ->
                            ObjectItemComposable(
                                objectItem =  obj,
                                onEdit = {
                                    // Redundant id parameter
                                    navController.navigate("${MainActivity.DETAIL_COMPOSABLE}/${obj.id}")
                                },
                                onDelete = {
                                    viewModel.deleteObject(obj)
                                },
                            )
                        }
                    }
                }

                is UIState.Error -> {
                    Text("Error: ${result.message}")
                }
            }
        }
    }
}

@Composable
@Preview
private fun ObjectListViewPreview() {

    DIPreviewWrapper {
        val viewModel = koinInject<ObjectListViewModel>()
        val navController = rememberNavController()
        ObjectListMainView(viewModel, navController)
    }
}