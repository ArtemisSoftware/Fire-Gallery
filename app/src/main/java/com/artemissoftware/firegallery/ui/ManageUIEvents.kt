package com.artemissoftware.firegallery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.artemissoftware.common.composables.scaffold.models.FGUiScaffoldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ManageUIEvents(
    uiEvent: Flow<UiEvent>,
    scaffoldState: FGUiScaffoldState,
    onNavigate: (UiEvent.Navigate) -> Unit = {},
    onNavigatePopUpTo: (UiEvent.NavigatePopUpTo) -> Unit = {},
    onChangeCurrentPositionBottomBar: (UiEvent.ChangeCurrentPositionBottomBar) -> Unit = {},
    onPopBackStack: () -> Unit = {},
    onRedirect: (UiEvent.Redirect) -> Unit = {},
    onFinishAndStartActivity: (UiEvent.FinishAndStartActivity) -> Unit = {},
    onDeepLink: () -> Unit = {},
) {

    LaunchedEffect(key1 = true) {

        uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowDialog -> {
                    scaffoldState.showDialog(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                is UiEvent.Redirect -> { onRedirect.invoke(event) }
                is UiEvent.Navigate -> { onNavigate(event) }
                is UiEvent.ChangeCurrentPositionBottomBar -> { onChangeCurrentPositionBottomBar(event) }
                is UiEvent.NavigatePopUpTo -> { onNavigatePopUpTo(event) }
                is UiEvent.FinishAndStartActivity -> { onFinishAndStartActivity(event) }
                is UiEvent.DeepLink -> { onDeepLink() }
            }
        }
    }
}