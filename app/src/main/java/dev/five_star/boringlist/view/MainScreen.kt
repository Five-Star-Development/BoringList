package dev.five_star.boringlist.view

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.five_star.boringlist.model.BoringDatabase
import dev.five_star.boringlist.model.BoringItem
import dev.five_star.boringlist.model.InMemoryBoringService
import dev.five_star.boringlist.ui.theme.BoringListTheme
import dev.five_star.boringlist.viewmodel.MainViewModel
import dev.five_star.boringlist.viewmodel.MainViewState

const val TAG = "MainScreen"

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {

    val context = LocalContext.current
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val boringDao = BoringDatabase.getDatabase(context = context).boringDao()
            val repository = InMemoryBoringService(boringDao)

            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                boringRepository = repository
            ) as T
        }
    }

    val mainViewModel: MainViewModel = viewModel(null, factory)
    val currentState: State<MainViewState> = mainViewModel.stateView.collectAsState()

    MainScreenContent(state = currentState.value, mainViewModel = mainViewModel)
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun MainScreenContent(state: MainViewState, mainViewModel: MainViewModel) {

    val showDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("My boring list") }) },
        floatingActionButton = { AddItem { showDialog.value = true } }, content = {
            BoringList(names = state.boringList, mainViewModel)
            // Create dialog, pass the showDialog state to this Composable
            AddDialog(showDialog, mainViewModel)
        })
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
private fun BoringList(names: List<BoringItem>, mainViewModel: MainViewModel) {

    LazyColumn(
        reverseLayout = true,
    ) {
        items(items = names, key = { item -> item.id }) { name ->
            SwipeDismissItem(
                background = {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Gray))
                },
                content = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        BoringListItem(name)
                        Divider()
                    }
                },
                onDismissed = { isDismissed ->
                    if (isDismissed) {
                        mainViewModel.removeListItem(name)
                    }
                }
            )
        }
    }
}

@Composable
private fun AddItem(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        }
    ) {
        Icon(Icons.Filled.Add, "")
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BoringListTheme {
        MainScreen()
    }
}