package dev.five_star.boringlist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.five_star.boringlist.ui.theme.BoringListTheme

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent(mainViewModel = MainViewModel())
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    BoringListTheme {
        content()
    }
}

@Composable
fun MyScreenContent(mainViewModel: MainViewModel = viewModel()) {
    val names: List<String> by mainViewModel.names.observeAsState(emptyList())
    // State to manage if the alert dialog is showing or not.
    // Default is false (not showing)
    val showDialog=  remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = {Text("My boring list")}) },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { showDialog.value = true } }, content = {
            BoringList(names = names)
            // Create alert dialog, pass the showDialog state to this Composable
            DialogDemo(showDialog)
        })
}

@Composable
fun BoringList(names: List<String>) {
    LazyColumn(reverseLayout = true) {
        items(items = names) { name ->
            Column(modifier = Modifier.padding(24.dp)) {
                Text(text = name, style = MaterialTheme.typography.body1)
                Text(text = name, style = MaterialTheme.typography.caption)
            }
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun AddItem(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
//            addListItem()
            onClick()
        }
    ) {
        Icon(Icons.Filled.Add, "")
    }
}
@Composable
fun DialogDemo(showDialog: MutableState<Boolean>, mainViewModel: MainViewModel = viewModel()) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { showDialog.value = false}) {
            Surface(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(modifier = Modifier.align(Alignment.TopStart),
                        text = "When I am board I will:")
                    var text by remember { mutableStateOf(TextFieldValue("")) }

                    TextField(
                        value = text,
                        onValueChange = {
                            text = it
                        },
                        label = { Text("Todo") })

                    Button(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        onClick = {
                        mainViewModel.addListItem(text.text)
                        showDialog.value = false
                    }) {
                        Text(text = "Add")
                    }
                }
            }
        }
//        AlertDialog(
//            onDismissRequest = {
//            },
//            title = {
//                Text("Title")
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        // Change the state to close the dialog
//                        setShowDialog(false)
//                        addItem()
//                    },
//                ) {
//                    Text("Confirm")
//                }
//            },
//            dismissButton = {
//                Button(
//                    onClick = {
//                        // Change the state to close the dialog
//                        setShowDialog(false)
//                    },
//                ) {
//                    Text("Dismiss")
//                }
//            },
//            text = {
//                Text("This is a text on the dialog")
//            },
//        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}