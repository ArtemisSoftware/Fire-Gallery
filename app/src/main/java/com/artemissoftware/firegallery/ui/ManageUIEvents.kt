package com.artemissoftware.firegallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    scaffoldState: FGScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    onPopBackStack: () -> Unit = {}
) {

    LaunchedEffect(key1 = true) {

        uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowDialog -> {
                    scaffoldState.showDialog(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                is UiEvent.Navigate -> onNavigate(event)
                else ->{}
            }
        }
    }
}