import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.awt.FileDialog
import java.io.File

class Logic {
    var prefix by mutableStateOf("")
    var postfix by mutableStateOf("")
    var filesName by mutableStateOf("")
    var isBefore by mutableStateOf(true)
    var setOfFiles by mutableStateOf(setOf<File>())
    val state = MutableSharedFlow<String>()
    fun save()  {
        setOfFiles.forEachIndexed { index, it ->
            if (!it.renameTo(File(it.parent, withRename(index) + ".${it.extension}"))) {
                CoroutineScope(Job()).launch{
                    state.emit("faild to rename ${it.name}")
                }
            }
        }
    }

    fun withRename(number: Int): String =
        if (isBefore)
            "$prefix$number$postfix$filesName"
        else
            "$filesName$prefix$number$postfix"


}

fun openFileDialog(
    title: String,
    allowedExtensions: List<String>,
): Set<File> {
    return FileDialog(ComposeWindow(), title, FileDialog.LOAD).apply {
        isMultipleMode = true
        // windows
        file = allowedExtensions.joinToString(";") { "*$it" } // e.g. '*.jpg'

        isVisible = true
    }.files.toSet()
}

