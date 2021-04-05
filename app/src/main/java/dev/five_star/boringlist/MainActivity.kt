package dev.five_star.boringlist

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.five_star.boringlist.ui.theme.BoringListTheme
const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
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
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    Scaffold(topBar = { } ,
        //floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = { AddItem() }
        , content = {
            BoringList(names = names)
        })
}

@Composable
fun BoringList(names: List<String>) {
    LazyColumn() {
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
fun AddItem() {
    FloatingActionButton(
        onClick = { Log.d(TAG, "FAB clicked")}
    ) {
        Icon(Icons.Filled.Add,"")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}