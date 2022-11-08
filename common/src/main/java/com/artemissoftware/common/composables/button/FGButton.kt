package com.artemissoftware.common.composables.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.LightBlue
import com.artemissoftware.common.theme.Orange

@Composable
fun FGButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    backgroundColor: Color = Orange,
    disabledBackgroundColor: Color = LightBlue,
) {

    Button(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = LightBlue
        ),
        content = {
            FGText(text = text)
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun FGButtonPreview() {

    Column(verticalArrangement = Arrangement.spacedBy(36.dp)) {
        FGButton(text = "First option", onClick = {})
        FGButton(text = "Second option", onClick = {}, enabled = false)
    }

}
