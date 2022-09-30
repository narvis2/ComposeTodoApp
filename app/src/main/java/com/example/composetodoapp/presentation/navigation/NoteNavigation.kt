package com.example.composetodoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetodoapp.presentation.screen.NoteDetailScreen
import com.example.composetodoapp.presentation.screen.NoteScreen
import com.example.composetodoapp.presentation.ui.NoteViewModel
import kotlinx.coroutines.CoroutineScope

@ExperimentalComposeUiApi
@Composable
fun NoteNavigation(viewModel: NoteViewModel, coroutineScope: CoroutineScope) {
    val navController = rememberNavController()
    val notes = viewModel.requestGetAllNoteList.collectAsState()

    return NavHost(navController = navController, startDestination = NavigationType.HOMESCREEN.name) {
        composable(NavigationType.HOMESCREEN.name) {
            NoteScreen(
                navController = navController,
                notes = notes.value,
                onAddNote = viewModel::addNote,
                onRemoveNote = viewModel::removeNote,
                onRemoveAll = viewModel::removeAllNote,
                coroutineScope = coroutineScope
            )
        }

        composable(
            route = NavigationType.DETAILSCREEN.name,
        ) {
            NoteDetailScreen(navController = navController)
        }
    }
}