package com.artemissoftware.common.composables.snackbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FGSnackBar(
    //snackbarHostState: SnackbarHostState,
    type: FGSnackBarType = FGSnackBarType.INFO,
    icon: ImageVector? = null,
    description: String
) {

    Card(
        backgroundColor = type.backgroundColor,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            icon?.let {
                Icon(
                    tint = type.textColor,
                    imageVector = it,
                    contentDescription = "",
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Text(
                color = type.textColor,
                text = description
            )
        }

    }
}

@Preview
@Composable
private fun FGSnackBarPreview() {

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        FGSnackBar(
            type = FGSnackBarType.SUCCESS,
            icon = Icons.Default.Notifications,
            description = "snackbarData.message"
        )

        FGSnackBar(
            type = FGSnackBarType.ALERT,
            description = "snackbarData.message.message.message.message.message"
        )

        FGSnackBar(
            type = FGSnackBarType.ERROR,
            icon = Icons.Default.Notifications,
            description = "snackbarData.message.message.message.message.message.message.message.message.message.message.message.message.message"
        )

        FGSnackBar(
            type = FGSnackBarType.INFO,
            description = "snackbarData.message.message.message.message.message.message.message.message"
        )
    }
}

