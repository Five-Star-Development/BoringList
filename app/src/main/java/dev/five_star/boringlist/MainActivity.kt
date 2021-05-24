package dev.five_star.boringlist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.five_star.boringlist.model.InMemoryBoringService
import dev.five_star.boringlist.model.fakeData
import dev.five_star.boringlist.ui.theme.BoringListTheme
import dev.five_star.boringlist.view.MainScreen
import dev.five_star.boringlist.view.MainScreenContent
import dev.five_star.boringlist.viewmodel.MainViewModel
import dev.five_star.boringlist.viewmodel.MainViewState

const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    @ExperimentalMaterialApi
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainScreen()
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

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MainScreen()
    }
}