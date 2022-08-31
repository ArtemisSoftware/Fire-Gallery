package com.artemissoftware.firegallery.screens.profile.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.icon.FGCircularIcon
import com.artemissoftware.common.theme.FGStyle
import com.artemissoftware.common.theme.ToggleBlue

@Composable
fun ProfileOption(
    icon: ImageVector,
    iconColor: Color = Color.Green,
    iconBackgroundColor: Color? = null,
    description: String,
    isChecked: Boolean = false,
    onCheck: (Boolean) -> Unit
) {

    val checkedColor = ToggleBlue
    val uncheckedThumbColor = Color.LightGray

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {

        Box(modifier =Modifier
            .weight(0.1F),
        ){

            FGCircularIcon(
                icon = icon,
                iconColor = iconColor,
                iconBackgroundColor = iconBackgroundColor
            )
        }

        Text(text = description,
            style = FGStyle.TextMeOne,
            modifier = Modifier
                .weight(0.8F)
                .padding(horizontal = 12.dp)
        )

        Switch(
            colors = SwitchDefaults.colors(
                checkedThumbColor = checkedColor,
                checkedTrackColor = checkedColor,
                uncheckedThumbColor = uncheckedThumbColor,
                uncheckedTrackColor = uncheckedThumbColor,
                checkedTrackAlpha = 0.1f,
                uncheckedTrackAlpha = 0.1F
            ),
            modifier = Modifier.weight(0.1F),
            checked = isChecked,
            onCheckedChange = {
                onCheck.invoke(it)
            }
        )

    }
}


@Preview
@Composable
fun ProfileOptionPreview() {

    Column {
        ProfileOption(icon = Icons.Filled.LocationOn, description = "description", onCheck = {}, isChecked = true)
        ProfileOption(icon = Icons.Filled.LocationOn, description = "descriptiondescriptiondescriptiondescription", onCheck = {})
    }

}