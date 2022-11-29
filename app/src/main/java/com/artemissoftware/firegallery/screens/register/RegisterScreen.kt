package com.artemissoftware.firegallery.screens.register

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


@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel
) {

    val state = viewModel.state.collectAsState()

    BuildRegisterScreen(
        state = state.value,
        events = viewModel::onTriggerEvent,
        email = viewModel.email,
        username = viewModel.username,
        password = viewModel.password,
        confirmPassword = viewModel.confirmPassword
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BuildRegisterScreen(
    state: RegisterState,
    events: ((RegisterEvents) -> Unit)? = null,
    email: String,
    username: String,
    password: String,
    confirmPassword: String
) {


    FGScaffold(
        isLoading = state.isLoading,
        showTopBar = true,
        onNavigationClick = {
            events?.invoke(RegisterEvents.PopBackStack)
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
                    text = email,
                    onValueChange = { email->
                        events?.invoke(RegisterEvents.ValidateRegister(email = email))
                    },
                    label = stringResource(R.string.email)
                )

                FGOutlinedTextField(
                    text = username,
                    maxChar = state.validationRules?.usernameMaxLength,
                    onValueChange = { text->
                        events?.invoke(RegisterEvents.ValidateRegister(username = text))
                    },
                    label = stringResource(R.string.user_name)
                )


                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = password,
                    maxChar = state.validationRules?.passwordMaxLength,
                    onValueChange = { password->
                        events?.invoke(RegisterEvents.ValidateRegister(password = password))
                    },
                    label = stringResource(R.string.password)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = confirmPassword,
                    maxChar = state.validationRules?.passwordMaxLength,
                    onValueChange = { confirmPassword->
                        events?.invoke(RegisterEvents.ValidateRegister(passwordConfirm = confirmPassword))
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
                        events?.invoke(RegisterEvents.Register)
                    }
                )

            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {

    val state = RegisterState()

    BuildRegisterScreen(state = state, email = "email", username = "username",
        password = "password",
        confirmPassword = "confirmPassword")
}