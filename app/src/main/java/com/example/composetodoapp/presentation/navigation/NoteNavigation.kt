package com.example.composetodoapp.presentation.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.composetodoapp.R
import com.example.composetodoapp.presentation.components.CustomDialog
import com.example.composetodoapp.presentation.screen.NoteDetailScreen
import com.example.composetodoapp.presentation.screen.NoteScreen
import com.example.composetodoapp.presentation.ui.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun NoteNavigation(viewModel: NoteViewModel, coroutineScope: CoroutineScope) {
    val navController = rememberNavController()
    // SnackBar
    val scaffoldState = rememberScaffoldState()

    // Note
    val notes = viewModel.requestGetAllNoteList.collectAsState()
    val note = viewModel.currentNote.collectAsState()

    // CustomDialog
    val customDialogTitle = viewModel.customDialogTitle.collectAsState()
    val customDialogConfirmText = viewModel.customDialogConfirmText.collectAsState()
    val customDialogCancelText = viewModel.customDialogCancelText.collectAsState()

    return NavHost(navController = navController, startDestination = NavigationType.HOMESCREEN.name) {
        composable(NavigationType.HOMESCREEN.name) {
            NoteScreen(
                navController = navController,
                notes = notes.value,
                onAddNote = viewModel::addNote,
                coroutineScope = coroutineScope,
                setCurrentNote = viewModel::setCurrentNote,
                scaffoldState = scaffoldState,
                setCustomDialogCancelText = viewModel::setCustomDialogCancelText,
                setCustomDialogConfirmText = viewModel::setCustomDialogConfirmText,
                setCustomDialogTitle = viewModel::setCustomDialogTitle
            )
        }

        composable(
            route = NavigationType.DETAILSCREEN.name,
        ) {
            NoteDetailScreen(
                navController = navController,
                note.value,
                setCustomDialogCancelText = viewModel::setCustomDialogCancelText,
                setCustomDialogConfirmText = viewModel::setCustomDialogConfirmText,
                setCustomDialogTitle = viewModel::setCustomDialogTitle,
                setCurrentNote = viewModel::setCurrentNote,
            )
        }

        dialog(
            route = NavigationType.CUSTOMDIALOG.name,
            dialogProperties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            )
        ) {
            CustomDialog(
                navController = navController,
                value = customDialogTitle.value.first,
                valueRes= customDialogTitle.value.second,
                confirmText = customDialogConfirmText.value,
                cancelText = customDialogCancelText.value,
            ) {
                coroutineScope.launch {
                    if (customDialogTitle.value.second == R.string.dialog_all_remove_title) {
                        viewModel.removeAllNote()
                        scaffoldState.snackbarHostState.showSnackbar("전체 메모를 삭제하였습니다.")
                        return@launch
                    }

                    viewModel.currentNote.value?.let {
                        viewModel.removeNote(it)
                        scaffoldState.snackbarHostState.showSnackbar("${it.title}를 삭제하였습니다.")
                    }
                }
            }
        }
    }
}