package dev.five_star.boringlist

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyScreenContent(mainViewModel: MainViewModel = viewModel()) {
    val names: List<BoringItem> by mainViewModel.names.observeAsState(emptyList())
    // State to manage if the alert dialog is showing or not.
    // Default is false (not showing)
    val showDialog = remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("My boring list") }) },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { showDialog.value = true } }, content = {
            BoringList(names = names)
            // Create alert dialog, pass the showDialog state to this Composable
            DialogDemo(showDialog)
        })
}

@Composable
private fun BoringList(names: List<BoringItem>) {
    LazyColumn(reverseLayout = true) {
        items(items = names) { name ->
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
    mainViewModel: MainViewModel = viewModel()
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