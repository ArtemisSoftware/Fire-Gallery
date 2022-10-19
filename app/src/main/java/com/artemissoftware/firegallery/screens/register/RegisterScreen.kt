package com.artemissoftware.firegallery.screens.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGButton
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.composables.textfield.FGOutlinedTextField
import com.artemissoftware.common.composables.textfield.FGTextFieldType
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.splash.composables.Logo


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen() {


    var email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val passwordConfirm = remember { mutableStateOf(TextFieldValue()) }
    
    FGScaffold(
        modifier = Modifier
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
                    },
                    label = stringResource(R.string.email)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = password.value,
                    onValueChange = { text->
                        password.value = text
                    },
                    label = stringResource(R.string.password)
                )

                FGOutlinedTextField(
                    fgTextFieldType = FGTextFieldType.PASSWORD,
                    text = passwordConfirm.value,
                    onValueChange = { text->
                        passwordConfirm.value = text
                    },
                    label = stringResource(R.string.confirm_password)
                )

                Spacer(modifier = Modifier.height(16.dp))

                FGButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.register),
                    onClick = {}
                )

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen()
}