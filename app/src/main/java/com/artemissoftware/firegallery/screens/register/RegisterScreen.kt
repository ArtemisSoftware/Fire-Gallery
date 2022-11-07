package com.artemissoftware.firegallery.screens.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
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
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    onPopBackStack: () -> Unit,
    scaffoldState: FGScaffoldState,
) {

    val viewModel: RegisterViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()


    ManageUiEvents(
        uiEvent = viewModel.uiEventLolo,
        onPopBackStack = onPopBackStack,
        scaffoldState = scaffoldState
    )

    BuildRegisterScreen(
        onPopBackStack = onPopBackStack,
        state = state.value,
        events = viewModel::onTriggerEvent
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BuildRegisterScreen(
    onPopBackStack: () -> Unit,
    state: RegisterState,
    events: ((RegisterEvents) -> Unit)? = null,
) {

    val email = remember { mutableStateOf(TextFieldValue()) }
    val username = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val passwordConfirm = remember { mutableStateOf(TextFieldValue()) }

    val validateRegister = {
        events?.invoke(
            RegisterEvents.ValidateRegister(
                email = email.value.text,
                username = username.value.text,
                password = password.value.text,
                passwordConfirm = passwordConfirm.value.text
            )
        )
    }

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
                            text = stringResource(R.string.create_new_account),
                            style = FGStyle.TextAlbertSansBold28
                        )
                        FGText(
                            text = stringResource(R.string.create_new_account_to_use_features),
                            style = FGStyle.TextAlbertSans
                        )
                    }

                }


                Spacer(modifier = Modifier.height(20.dp))

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.EMAIL,
                    text = email.value,
                    onValueChange = { text->
                        email.value = text
                        validateRegister.invoke()
                    },
                    label = stringResource(R.string.email)
                )

                FGOutlinedTextField(
                    text = username.value,
                    maxChar = state.validationRules?.usernameMaxLength,
                    onValueChange = { text->
                        username.value = text
                        validateRegister.invoke()
                    },
                    label = stringResource(R.string.user_name)
                )


                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = password.value,
                    maxChar = state.validationRules?.passwordMaxLength,
                    onValueChange = { text->
                        password.value = text
                        validateRegister.invoke()
                    },
                    label = stringResource(R.string.password)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = passwordConfirm.value,
                    maxChar = state.validationRules?.passwordMaxLength,
                    onValueChange = { text->
                        passwordConfirm.value = text
                        validateRegister.invoke()
                    },
                    label = stringResource(R.string.confirm_password),
                    imeAction = ImeAction.Done
                )

                Spacer(modifier = Modifier.height(16.dp))

                FGButton(
                    enabled = state.isValidData,
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.register),
                    onClick = {
                        events?.invoke(
                            RegisterEvents.Register(
                                username = username.value.text,
                                email = email.value.text,
                                password = password.value.text
                            )
                        )
                    }
                )

            }
        }
    }
}


@Composable
private fun ManageUiEvents(
    uiEvent: Flow<UiEvent>,
    scaffoldState: FGScaffoldState,
    onPopBackStack: () -> Unit
) {

    LaunchedEffect(key1 = true) {

        uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowDialog -> {
                    scaffoldState.showDialog(event.dialogType)
                }
                is UiEvent.PopBackStack -> { onPopBackStack.invoke() }
                else ->{}
            }
        }
    }

}




@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {

    val state = RegisterState()

    BuildRegisterScreen(state = state, onPopBackStack = {})
}