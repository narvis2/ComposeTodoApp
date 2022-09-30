package com.example.composetodoapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.composetodoapp.presentation.screen.NoteScreen
import com.example.composetodoapp.presentation.theme.ComposeTodoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTodoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    NotesApp(noteViewModel = noteViewModel, lifecycleScope)
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun NotesApp(noteViewModel: NoteViewModel, coroutineScope: CoroutineScope) {
    /**
     * state holder -> NotesApp()
     * 실제 UI를 그리는 Composable -> NoteScreen()
     * --------------- 설명 ---------------------
     * UI를 직접 그리는 NoteScreen Composable 에는 ViewModel 을 직접 주입하지 않고,
     * state holder 를 담당하는 Composable 을 한 단계 거치도록 함
     */
    val noteList = noteViewModel.requestGetAllNoteList.collectAsState()

    NoteScreen(
        notes = noteList.value,
        onAddNote = noteViewModel::addNote,
        onRemoveNote = noteViewModel::removeNote,
        onRemoveAll = noteViewModel::removeAllNote,
        coroutineScope
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTodoAppTheme {

    }
}