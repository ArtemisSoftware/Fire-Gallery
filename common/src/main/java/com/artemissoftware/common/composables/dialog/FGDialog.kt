package com.artemissoftware.common.composables.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.artemissoftware.common.R
import com.artemissoftware.common.composables.dialog.models.DialogButtonType
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.icon.FGCircularIcon
import com.artemissoftware.common.composables.scaffold.models.FGUiScaffoldState
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.common.theme.primaryText
import kotlinx.coroutines.MainScope

@Composable
fun FGDialog(fgUiScaffoldState: FGUiScaffoldState) {

    fgUiScaffoldState.dialog.value?.let { dialogType->

        Dialog(
            onDismissRequest = { },
            content = {
                FGDialog(
                    fgUiScaffoldState = fgUiScaffoldState,
                    dialogType = dialogType
                )
            }
        )
    }

}



@Composable
private fun FGDialog(
    fgUiScaffoldState: FGUiScaffoldState,
    dialogType: DialogType
){
    val imageContent: @Composable () -> Unit = when{

        dialogType.imageId != null ->{
            {
                Image(
                    painter = painterResource(id = dialogType.imageId),
                    contentDescription = null, // decorative
//                contentScale = ContentScale.Fit,
                    colorFilter  = ColorFilter.tint(
                        color = dialogType.iconColor
                    ),
                    modifier = Modifier
                        .padding(top = 35.dp)
                        .height(70.dp)
                        .fillMaxWidth(),

                    )
            }
        }
        dialogType.icon != null ->{
            {
                Box(modifier = Modifier.fillMaxWidth()) {

                    FGCircularIcon(
                        modifier = Modifier
                            .padding(top = 35.dp)
                            .align(Alignment.Center),
                        icon = dialogType.icon,
                        iconColor = dialogType.iconColor,
                        backgroundAlpha = 0.1F,
                        size = 70.dp,
                        iconPadding = 12.dp
                    )
                }


            }
        }
        else ->{
            {}
        }
    }

    FGDialog(
        fgUiScaffoldState = fgUiScaffoldState,
        dialogType = dialogType,
        imageContent = imageContent
    )

}



@Composable
private fun FGDialog(
    fgUiScaffoldState: FGUiScaffoldState,
    dialogType: DialogType,
    imageContent: @Composable () -> Unit
){

    val textColor = when(dialogType){

        is DialogType.Error, is DialogType.Info ->{
            Color.White
        }

        else -> MaterialTheme.colors.primaryText
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 5.dp, bottom = 10.dp),
        elevation = 8.dp
    ) {
        Column {

            imageContent()

            FGDialogMessage(dialogType = dialogType)

            FGDialogOptions(
                fgUiScaffoldState = fgUiScaffoldState,
                mainColor = dialogType.mainColor,
                textColor = textColor,
                dialogOptions = dialogType.dialogOptions
            )
        }
    }
}



@Composable
private fun FGDialogMessage(dialogType: DialogType){

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        FGText(
            text = dialogType.title,
            style = FGStyle.TextAlbertSansBold,
            modifier = Modifier
                .padding(top = 5.dp)
                .fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        FGText(
            text = dialogType.description,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
        )
    }

}


@Composable
private fun FGDialogOptions(
    fgUiScaffoldState: FGUiScaffoldState,
    mainColor : Color,
    textColor: Color = MaterialTheme.colors.primaryText,
    dialogOptions: DialogOptions
){

    val confirmModifier = if(dialogOptions.getOptionsType() == DialogButtonType.DOUBLE_OPTION) Modifier else Modifier.fillMaxWidth()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(mainColor),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        if(dialogOptions.getOptionsType() == DialogButtonType.DOUBLE_OPTION){
            TextButton(
                onClick = {
                    dialogOptions.cancel()
                    fgUiScaffoldState.closeDialog()
                }
            ) {

                FGText(
                    text = stringResource(id = dialogOptions.cancelTextId?: R.string.cancel) ,
                    style = FGStyle.TextAlbertSansBold,
                    color = textColor.copy(alpha = 0.4F),
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }

        TextButton(
            modifier = confirmModifier,
            onClick = {
                dialogOptions.confirmation()
                fgUiScaffoldState.closeDialog()
            }
        ) {
            FGText(
                text = stringResource(id = dialogOptions.confirmationTextId),
                style = FGStyle.TextAlbertSansBold,
                color = textColor,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}





@Preview
@Composable
private fun FGDialogMessagePreview(){

    val dialogTypeSuccess = DialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        icon = Icons.Filled.Build,
        dialogOptions = DialogOptions(
            confirmationTextId = R.string.confirm,
            cancelTextId = R.string.cancel
        )
    )

    FGDialogMessage(dialogType = dialogTypeSuccess)
}




@Preview
@Composable
private fun FGDialogOptionsPreview(){

    val dialogTypeSuccess = DialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        icon = Icons.Filled.Build,
        dialogOptions = DialogOptions(
            confirmationTextId = R.string.confirm,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = DialogType.Error(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        imageId = R.drawable.ic_android,
        dialogOptions = DialogOptions(
            confirmationTextId = R.string.confirm,
        )
    )

    Column {
        FGDialogOptions(fgUiScaffoldState = FGUiScaffoldState(MainScope()), mainColor = dialogTypeSuccess.mainColor, dialogOptions = dialogTypeSuccess.dialogOptions)
        FGDialogOptions(fgUiScaffoldState = FGUiScaffoldState(MainScope()), mainColor = dialogTypError.mainColor, dialogOptions = dialogTypError.dialogOptions)
    }
}


@Preview
@Composable
private fun FGDialogPreview(){

    val dialogTypeSuccess = DialogType.Success(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        icon = Icons.Filled.Build,
        dialogOptions = DialogOptions(
            confirmationTextId = R.string.confirm,
            cancelTextId = R.string.cancel
        )
    )

    val dialogTypError = DialogType.Error(
        title =  "Get updates",
        description = "Allow permission to send notifications every day of the year",
        imageId = R.drawable.ic_android,
        dialogOptions = DialogOptions(
            confirmationTextId = R.string.confirm
        )
    )

    Column {
        FGDialog(FGUiScaffoldState(MainScope()), dialogTypeSuccess)
        FGDialog(FGUiScaffoldState(MainScope()), dialogTypError)
    }
}
