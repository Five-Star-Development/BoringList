package dev.five_star.boringlist.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
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

@Composable
fun MainScreenContent(state: MainViewState, mainViewModel: MainViewModel) {

    val showDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("My boring list") }) },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { showDialog.value = true } }, content = {
            BoringList(names = state.boringList, mainViewModel)
            // Create dialog, pass the showDialog state to this Composable
            AddDialog(showDialog, mainViewModel)
        })
}

@Composable
private fun BoringList(names: List<BoringItem>, mainViewModel: MainViewModel) {
    LazyColumn(reverseLayout = true) {
        items(items = names) { name ->
            Column {
                BoringListItem(name = name, mainViewModel = mainViewModel)
                Divider(color = Color.Black)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoringListItem(name: BoringItem, mainViewModel: MainViewModel) {
    var unread by remember { mutableStateOf(false) }
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) unread = !unread
            it != DismissValue.DismissedToEnd
        }
    )

    if (unread) {
        Log.d(TAG, "remove $name")
        mainViewModel.removeListItem(name)
        unread = false
    }

    SwipeToDismiss(state = dismissState, background = { }) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = name.todo, style = MaterialTheme.typography.body1)
            if (name.description.isNotBlank()) {
                Text(text = name.description, style = MaterialTheme.typography.caption)
            }
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BoringListTheme {
        MainScreen()
    }
}