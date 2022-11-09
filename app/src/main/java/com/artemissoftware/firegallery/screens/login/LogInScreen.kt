package com.artemissoftware.firegallery.screens.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.common.composables.button.FGButton
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.composables.textfield.FGOutlinedTextField
import com.artemissoftware.common.composables.textfield.FGTextFieldType
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.splash.composables.Logo
import com.artemissoftware.firegallery.ui.ManageUIEvents
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LogInScreen(
    onPopBackStack: () -> Unit,
    scaffoldState: FGScaffoldState,
) {

    val viewModel: LogInViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    ManageUIEvents(
        uiEvent = viewModel.uiEventLolo,
        onPopBackStack = onPopBackStack,
        scaffoldState = scaffoldState
    )

    BuildLogInScreen(
        onPopBackStack = onPopBackStack,
        state = state.value,
        events = viewModel::onTriggerEvent,
        email = viewModel.email,
        password = viewModel.password
    )

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BuildLogInScreen(
    onPopBackStack: () -> Unit,
    state: LogInState,
    events: ((LogInEvents) -> Unit)? = null,
    email: String,
    password: String
) {

    FGScaffold(
        isLoading = state.isLoading,
        showTopBar = true,
        onNavigationClick = {
            onPopBackStack.invoke()
        }
    ) {


        Box(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)){

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){

                Box(modifier = Modifier.fillMaxWidth()) {

                    Logo(modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.CenterEnd))

                    Column(
                        modifier = Modifier.align(Alignment.CenterStart),
                        verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        FGText(
                            text = stringResource(R.string.welcome_back),
                            style = FGStyle.TextAlbertSansBold28
                        )
                        FGText(
                            text = stringResource(R.string.log_in_to_account),
                            style = FGStyle.TextAlbertSans
                        )
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.EMAIL,
                    text = email,
                    onValueChange = { email->
                        events?.invoke(LogInEvents.ValidateLogin(email = email))
                    },
                    label = stringResource(R.string.email)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = password,
                    onValueChange = { password->
                        events?.invoke(LogInEvents.ValidateLogin(password = password))
                    },
                    label = stringResource(R.string.password),
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(16.dp))

                FGButton(
                    enabled = state.isValidData,
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.log_in),
                    onClick = {
                        events?.invoke(LogInEvents.LogIn)
                    }
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BuildLogInScreenPreview() {

    val state = LogInState()

    BuildLogInScreen(state = state, onPopBackStack = {}, email ="email", password = "password")

}