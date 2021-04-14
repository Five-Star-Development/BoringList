package dev.five_star.boringlist.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.five_star.boringlist.model.BoringDatabase
import dev.five_star.boringlist.model.BoringItem
import dev.five_star.boringlist.model.InMemoryBoringService
import dev.five_star.boringlist.model.fakeData
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

    val mainViewModel : MainViewModel = viewModel(null, factory)
    val currentState: State<MainViewState> = mainViewModel.stateView.collectAsState()
    
    MainScreenContent(state = currentState.value, mainViewModel = mainViewModel)
}

@Composable
fun MainScreenContent(state: MainViewState, mainViewModel : MainViewModel) {

    val showDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("My boring list") }) },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { showDialog.value = true } }, content = {
            BoringList(names = state.boringList)
            // Create alert dialog, pass the showDialog state to this Composable
            DialogDemo(showDialog, mainViewModel)
        })
}

@Composable
private fun BoringList(names: List<BoringItem>) {
    LazyColumn(reverseLayout = true) {
        items(items = names) { name ->
            Column {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = name.todo, style = MaterialTheme.typography.body1)
                    if (name.description.isNotBlank()) {
                        Text(text = name.description, style = MaterialTheme.typography.caption)
                    }
                }
                Divider(color = Color.Black)
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

@Composable
private fun DialogDemo(
    showDialog: MutableState<Boolean>,
    mainViewModel: MainViewModel
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false }) {
            Surface(
                modifier = Modifier
                    .height(280.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                var todo by remember { mutableStateOf(TextFieldValue("")) }
                var description by remember { mutableStateOf(TextFieldValue("")) }
                var isChecked by remember { mutableStateOf(false) }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(modifier = Modifier.align(Alignment.TopStart)) {

                        Text(
                            modifier = Modifier.padding(bottom = 8.dp),
                            text = "When I am board I will:"
                        )

                        TextField(
                            value = todo,
                            onValueChange = {
                                todo = it
                            },
                            label = { Text("Todo") })

                        Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
                            Checkbox(
                                checked = isChecked,
                                modifier = Modifier.padding(end = 8.dp),
                                onCheckedChange = { isChecked = isChecked.not() }
                            )
                            Text(text = "Description")
                        }

                        if (isChecked) {
                            TextField(
                                value = description,
                                onValueChange = {
                                    description = it
                                },
                                label = { Text("Description") })
                        }
                    }

                    Button(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = {
                            mainViewModel.addListItem(todo.text, description.text)
                            showDialog.value = false
                        }) {
                        Text(text = "Add")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    BoringListTheme {
        MainScreen()
    }
}