package dev.five_star.boringlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
    Scaffold(topBar = { },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { setShowDialog(true) } }, content = {
            BoringList(names = names)
            // Create alert dialog, pass the showDialog state to this Composable
            DialogDemo(showDialog, setShowDialog, { mainViewModel.addListItem() })
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
fun DialogDemo(showDialog: Boolean, setShowDialog: (Boolean) -> Unit, addItem: () -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
            },
            title = {
                Text("Title")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                        addItem()
                    },
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Change the state to close the dialog
                        setShowDialog(false)
                    },
                ) {
                    Text("Dismiss")
                }
            },
            text = {
                Text("This is a text on the dialog")
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}