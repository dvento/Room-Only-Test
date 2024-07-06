package com.danvento.saaldigitaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danvento.saaldigitaltest.core.utils.DIPreviewWrapper
import com.danvento.saaldigitaltest.ui.theme.SaalDigitalTestTheme
import com.danvento.saaldigitaltest.ui.view.ObjectDetailScreen
import com.danvento.saaldigitaltest.ui.view.ObjectListScreen

class MainActivity : ComponentActivity() {

    companion object {
        const val LIST_COMPOSABLE = "objectlist"
        const val DETAIL_COMPOSABLE = "objectdetail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            SaalDigitalTestTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = LIST_COMPOSABLE
                    ) {
                        composable(
                            route = LIST_COMPOSABLE
                        ) {
                            ObjectListView()
                        }
                        composable(route = "$DETAIL_COMPOSABLE/{objectId}") { backStackEntry ->
                            ObjectDetailView(
                                backStackEntry.arguments?.getInt("objectId") ?: 0
                            )
                        }
                    }
                }*/


                MainContent()
            }
        }
    }
}

@Composable
fun MainContent() {

    val navController = rememberNavController()
    var showFab by remember { mutableStateOf(true) }
    var fabAction: (() -> Unit)? by remember { mutableStateOf(null) }

    Scaffold(
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(onClick = { fabAction?.invoke() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Object")
                }
            }
        },

        ) {
        AppNavGraph(navController, onFabAction = { action, show ->
            fabAction = action
            showFab = show
        })
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    onFabAction: (action: (() -> Unit)?, show: Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = MainActivity.LIST_COMPOSABLE) {
        composable(route = MainActivity.LIST_COMPOSABLE) {
            ObjectListScreen(
                navController,
                onFabAction
            )
        }
        composable(route = "${MainActivity.DETAIL_COMPOSABLE}/{objectId}") { backStackEntry ->
            val objectId = backStackEntry.arguments?.getString("objectId")?.toInt()
            ObjectDetailScreen(navController, objectId, onFabAction)
        }
        composable(MainActivity.DETAIL_COMPOSABLE) {
            ObjectDetailScreen(navController, null, onFabAction)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DIPreviewWrapper {
        SaalDigitalTestTheme {
            MainContent()
        }
    }

}