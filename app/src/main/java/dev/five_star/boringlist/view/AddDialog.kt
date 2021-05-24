package dev.five_star.boringlist.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.five_star.boringlist.viewmodel.MainViewModel

@Composable
fun AddDialog(
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