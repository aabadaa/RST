import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.collect

@Composable
fun MainScreen(
    logic: Logic
) = logic.run {
    var log by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        state.collect {
            log += it + "\n"
        }
    }
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = CenterVertically
    ) {
        //settings
        Column(
            modifier = Modifier.weight(.3f),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = filesName, onValueChange = { filesName = it },
                label = { Text("files name") }
            )
            OutlinedTextField(value = prefix, onValueChange = { prefix = it },
                label = { Text("prefix") }
            )
            OutlinedTextField(value = postfix, onValueChange = { postfix = it },
                label = { Text("postfix") }
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                RadioButton(isBefore, onClick = { isBefore = true })
                Text("Before")
                RadioButton(!isBefore, onClick = { isBefore = false })
                Text("After")

            }
        }
        // file chooser and log
        Column(
            modifier = Modifier.weight(.4f),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

        )
        {
            Text("example : ${withRename(0)}")
            Button(onClick = { setOfFiles = openFileDialog("select files", listOf()) }) {
                Text("select files")
            }
            Button(onClick = { save() }) {
                Text("Save")
            }
            Text("log")
            Text(log)
        }

        //list example
        Column(modifier = Modifier.weight(.3f)) {
            Text("results")
            setOfFiles.mapIndexed { index, it ->
                Text(withRename(index) + ".${it.extension}")

            }
        }
    }
}