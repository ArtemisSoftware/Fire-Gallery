package com.artemissoftware.common.composables.textfield

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.common.theme.Orange
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun FGOutlinedTextField(
    modifier: Modifier = Modifier,
    fgTextFieldType: FGTextFieldType = FGTextFieldType.TEXT,
    label: String,
    text: String,
    textStyle: TextStyle = FGStyle.TextAlbertSans,
    maxChar: Int? = null,
    onValueChange: (String) -> Unit = {},
    borderColor: androidx.compose.ui.graphics.Color = Orange,
    imeAction: ImeAction = ImeAction.Next
) {

    val focusManager = LocalFocusManager.current
    val relocation = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()
    val textFormatted = text.take(maxChar ?: fgTextFieldType.getMaxChar())

    var isPasswordVisible = remember { mutableStateOf(fgTextFieldType != FGTextFieldType.PASSWORD) }


    OutlinedTextField(
        modifier = modifier.fillMaxWidth().bringIntoViewRequester(relocation)
            .onFocusEvent {
                if (it.isFocused) scope.launch { delay(300); relocation.bringIntoView() }
            },
        value = textFormatted,
        textStyle = textStyle,
        onValueChange = { text->
            onValueChange.invoke(text)
        },
        label = {
            FGText(
                text = label,
                style = FGStyle.TextAlbertSans12
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = borderColor,
            focusedLabelColor = borderColor,
            cursorColor = MaterialTheme.colors.primaryVariant
        ),
        keyboardOptions = KeyboardOptions(keyboardType = fgTextFieldType.getKeyboardType(), imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
            onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        visualTransformation = getVisualTransformation(isPasswordVisible.value),
        singleLine = true,
        trailingIcon = {
            TrailingIcon(
                fgTextFieldType = fgTextFieldType,
                onClick = { onValueChange.invoke(text) },
                text = text,
                isPasswordVisible = isPasswordVisible
            )
        }
    )


//    OutlinedTextField(
//        modifier = Modifier.fillMaxWidth(),
//        value = password,
//        onValueChange = { password = it },
//        label = { Text(text = "Password") },
//        singleLine = true,
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
//        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//        trailingIcon = {
//            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
//                Icon(
//                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                    contentDescription = "Password Toggle"
//                )
//            }
//        }
//    )

}

@Composable
private fun TrailingIcon(
    fgTextFieldType: FGTextFieldType,
    onClick: (TextFieldValue) -> Unit,
    text: String,
    isPasswordVisible: MutableState<Boolean>
) {

    when(fgTextFieldType){


        FGTextFieldType.PASSWORD ->{

            IconButton(onClick = {
                isPasswordVisible.value = !isPasswordVisible.value
            }) {
                Icon(
                    imageVector = if (isPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = ""
                )
            }
        }
        else ->{

            if (text.isNotBlank())
                IconButton(onClick = {
                    onClick.invoke(TextFieldValue(""))
                }) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = ""
                    )
                }
            
            
        }

    }


}

private fun getVisualTransformation(isPasswordVisible: Boolean) = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()

/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
@ExperimentalAnimationApi
private fun BuildEDPTextField(
    modifier: Modifier = Modifier,
//    type: EDPTextFieldType,
//    @DrawableRes iconId: Int?,
//    label: String?,
//    placeHolder: String?,
//    value: String,
//    onValueChange: (String) -> Unit,
//    countryPhone: CountryPhone = CountryPhone.PORTUGAL,
//    onCountryPhoneChange: ((CountryPhone) -> Unit)? = null,
//    maxLength: Int,
//    isEnabled: Boolean = true,
//    readOnly: Boolean = false,
//    isSuccess: Boolean = false,
//    isError: Boolean = false,
//    errorMessage: String?,
//    hintMessage: String?,
//    imeAction: ImeAction = ImeAction.Done,
//    optionMessageVisible: Boolean? = null,
//    optionTextMessage: String? = null,
//    optionMessageOnClick: (() -> Unit)? = {},
) {
    val focusManager = LocalFocusManager.current
    val relocationRequester = remember { RelocationRequester() }
    val coroutineScope = rememberCoroutineScope()

    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(type == EDPTextFieldType.PASSWORD) }
    val isSuccessVisible = (isSuccess && !isFocused)
    val isShowClearButton = type.isShowClearButton()

    val isValidRegex: Boolean = type.getRegex()?.let { Regex(it).containsMatchIn(value) } ?: true

    val defaultBorderColor = MaterialTheme.colors.textFieldOutlineNormal
    val focusedBorderColor = MaterialTheme.colors.textFieldOutlineFocused
    val disabledBorderColor = MaterialTheme.colors.textFieldOutlineNormal.copy(alpha = 0.7f)
    val successBorderColor = MaterialTheme.colors.green
    val errorBorderColor = MaterialTheme.colors.red
    val searchBorderColor = MaterialTheme.colors.transparent

    val optionMessage = optionTextMessage ?: translateResource(R.string.login_entry_forgot_password)

    val maxLines = if (type == EDPTextFieldType.TEXT_AREA) 10 else 1
    val minHeight = if (type == EDPTextFieldType.TEXT_AREA) 144.dp else 48.dp

    val colorBorder by animateColorAsState(
        targetValue = when {
            (type == EDPTextFieldType.SEARCH) -> searchBorderColor
            (!isEnabled || readOnly) -> disabledBorderColor
            (isSuccessVisible) -> successBorderColor
            (isError || errorMessage != null) -> errorBorderColor
            (!isFocused) -> defaultBorderColor
            else -> focusedBorderColor
        },
        animationSpec = tween()
    )

    Column(
        modifier = modifier
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            label?.let {
                EDPText(text = it, color = MaterialTheme.colors.secondaryText)
            }

            optionMessageVisible?.let {
                EDPText(
                    text = optionMessage,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .clickable {
                            optionMessageOnClick?.invoke()
                        }
                )
            }

        }
        Spacer(Modifier.height(4.dp))
        BasicTextField(
            modifier = Modifier
                .clearFocusOnKeyboardDismiss()
                .relocationRequester(relocationRequester)
                .bringIntoViewWhenFocused(relocationRequester)
                .onFocusChanged { isFocused = it.isFocused },
            cursorBrush = SolidColor(MaterialTheme.colors.primaryText),
            value = value.replace("\\s+".toRegex(), " "),
            onValueChange = { newValue ->
                if ((newValue.length <= maxLength) && (isValidRegex)) {
                    onValueChange(
                        when (type) {
                            EDPTextFieldType.NAME -> {
                                newValue.filter { it.isLetter() || it.isWhitespace() }

                            }
                            EDPTextFieldType.NICKNAME -> {
                                newValue.filter { it.isLetterOrDigit() || it.isWhitespace() }
                            }

                            EDPTextFieldType.PLATE -> {
                                newValue.filter {
                                    it.isLetterOrDigit() || it.toString() == "-"
                                }
                            }

                            else -> {
                                newValue
                            }
                        }
                    )
                }
            },
            textStyle = EDPStyle.Text.copy(MaterialTheme.colors.primaryText.copy(alpha = if (isEnabled || readOnly) 1f else 0.7f), textAlign = if(readOnly) TextAlign.Center else TextAlign.Start),
            readOnly = !isEnabled || readOnly,
            maxLines = maxLines,
            singleLine = (type != EDPTextFieldType.TEXT_AREA),
            visualTransformation = if (isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = if (type == EDPTextFieldType.PLATE) KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Characters
            ) else KeyboardOptions.Default.copy(
                keyboardType = type.getKeyboardType(),
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            decorationBox = { innerTextField ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = minHeight)
                        .clip(RoundedCornerShape(if (type == EDPTextFieldType.SEARCH) 32.dp else 8.dp))
                        .background(MaterialTheme.colors.textFieldBackground)
                        .border(
                            1.dp,
                            colorBorder,
                            RoundedCornerShape(if (type == EDPTextFieldType.SEARCH) 32.dp else 8.dp)
                        )
                        .padding(start = 16.dp, end = 12.dp),
                    contentAlignment = if(type == EDPTextFieldType.TEXT_AREA) Alignment.TopStart  else Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        iconId?.let {
                            Row(
                                modifier = Modifier.width(IntrinsicSize.Min)
                            ) {
                                EDPImage(
                                    modifier = Modifier.size(32.dp),
                                    resId = it,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primaryIcon)
                                )

                                Spacer(Modifier.width(if (type == EDPTextFieldType.SEARCH) 16.dp else 8.dp))
                            }
                        }

                        var expanded by remember { mutableStateOf(false) }
                        val listCountries = listOf(
                            CountryPhone.PORTUGAL,
                            CountryPhone.SPANISH
                        )

                        if (type == EDPTextFieldType.PHONE) {
                            Row(
                                modifier = Modifier.padding(end = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    Modifier.clickable { expanded = !expanded },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    EDPDropDownPhone(
                                        expanded = expanded,
                                        onExpanded = { expanded = it },
                                        listCountries = listCountries,
                                        onChangeCurrentCountry = { onCountryPhoneChange?.invoke(it) }
                                    )

                                    EDPImage(
                                        modifier = Modifier
                                            .width(24.dp)
                                            .height(20.dp),
                                        resId = countryPhone.getFlag(),
                                        contentScale = ContentScale.FillBounds,
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))
                                    EDPImage(
                                        modifier = Modifier.size(12.dp),
                                        resId = R.drawable.ic_down_thick,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colors.grey)
                                    )
                                }


                                Spacer(modifier = Modifier.width(8.dp))
                                DividerPhone()

                                Spacer(modifier = Modifier.width(12.dp))
                                EDPText(
                                    text = countryPhone.getPrefix(),
                                    color = MaterialTheme.colors.primaryText
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = if(type == EDPTextFieldType.TEXT_AREA) Alignment.Top else Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.9f)
                                    .padding(vertical = if (type == EDPTextFieldType.TEXT_AREA) 12.dp else 0.dp)
                            ) {
                                placeHolder?.let {
                                    this@Column.AnimatedVisibility(
                                        visible = value.isEmpty(),
                                        enter = fadeIn(0f, tween(durationMillis = 150)),
                                        exit = fadeOut(0f, tween(durationMillis = 150))
                                    ) {
                                        EDPText(
                                            text = it,
                                            color = MaterialTheme.colors.secondaryText
                                        )
                                    }
                                }

                                innerTextField()
                            }

                            Box(
                                modifier = if(type == EDPTextFieldType.TEXT_AREA) Modifier.height(144.dp) else Modifier,
                                contentAlignment = Alignment.Center
                            ) {
                                this@Column.AnimatedVisibility(
                                    visible = isSuccessVisible,
                                    enter = fadeIn(0f, tween(durationMillis = 300)),
                                ) {
                                    EDPImage(
                                        modifier = Modifier.size(24.dp),
                                        resId = R.drawable.ic_correct,
                                        colorFilter = ColorFilter.tint(MaterialTheme.colors.green)
                                    )
                                }

                                this@Column.AnimatedVisibility(
                                    visible = !isSuccessVisible && value.isNotEmpty() && isShowClearButton && isEnabled && !readOnly,
                                    enter = fadeIn(0f, tween(durationMillis = 300)),
                                ) {

                                    if (type == EDPTextFieldType.CAMERA_CAPTURE) {
                                        EDPIconButton(
                                            modifier = Modifier
                                                .size(32.dp),
                                            resId = R.drawable.ic_camera,
                                            color = MaterialTheme.colors.grey,
                                            padding = 0.dp,
                                            onClick = {

                                            }
                                        )
                                    }
                                    else {
                                        EDPIconButton(
                                            modifier = Modifier.size(24.dp),
                                            resId = R.drawable.ic_close,
                                            color = MaterialTheme.colors.grey,
                                            padding = 0.dp,
                                            onClick = {
                                                onValueChange("")
                                            }
                                        )
                                    }
                                }

                                if (type == EDPTextFieldType.PASSWORD && isEnabled) {
                                    EDPIconButton(
                                        modifier = Modifier
                                            .size(32.dp),
                                        resId = if (isPasswordVisible) R.drawable.ic_password_show else R.drawable.ic_password_hide,
                                        color = MaterialTheme.colors.grey,
                                        padding = 0.dp,
                                        onClick = {
                                            isPasswordVisible = !isPasswordVisible
                                        }
                                    )
                                }


                                if (type == EDPTextFieldType.CAMERA_CAPTURE) {
                                    EDPIconButton(
                                        modifier = Modifier
                                            .size(32.dp),
                                        resId = R.drawable.ic_camera,
                                        color = MaterialTheme.colors.grey,
                                        padding = 0.dp,
                                        onClick = {

                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )

        errorMessage?.let {
            Spacer(Modifier.height(2.dp))
            EDPText(
                text = it,
                color = MaterialTheme.colors.redVariant
            )
        }

        hintMessage?.let {
            Spacer(Modifier.height(2.dp))
            EDPText(
                text = it,
                color = MaterialTheme.colors.secondaryText
            )
        }
    }
}


*/

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun FGOutlinedTextFieldPreview() {

    var text = "Example"

    Column(verticalArrangement = Arrangement.spacedBy(36.dp)) {
        FGOutlinedTextField(
            text = text,
            label = "MyLAbel"
        )

        FGOutlinedTextField(
            fgTextFieldType = FGTextFieldType.EMAIL,
            text = text,
            label = "MyLAbel"
        )

        FGOutlinedTextField(
            fgTextFieldType = FGTextFieldType.PASSWORD,
            text = text,
            label = "MyLAbel"
        )

    }


}