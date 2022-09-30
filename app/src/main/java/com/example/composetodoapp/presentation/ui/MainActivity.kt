package com.example.composetodoapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.composetodoapp.presentation.navigation.NoteNavigation
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
                NotesApp(noteViewModel = noteViewModel, lifecycleScope)
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
    NoteNavigation(noteViewModel, coroutineScope)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTodoAppTheme {

    }
}