package dev.five_star.boringlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    Scaffold(topBar = { },
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem { mainViewModel.addListItem() } }, content = {
            BoringList(names = names)
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
fun AddItem(addListItem: () -> Unit) {
    FloatingActionButton(
        onClick = {
            addListItem()
        }
    ) {
        Icon(Icons.Filled.Add, "")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}