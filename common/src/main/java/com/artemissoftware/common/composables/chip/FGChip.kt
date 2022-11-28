package com.artemissoftware.common.composables.chip

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.models.Chip
import com.artemissoftware.common.theme.FGStyle.TextAlbertSans12
import com.artemissoftware.common.theme.Purple200
import com.artemissoftware.common.theme.Red
import com.artemissoftware.common.theme.RedOrange
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FGChip(
    chip: Chip,
    modifier: Modifier = Modifier
) {
    FGChip(
        text = chip.text,
        startBorderColor = chip.getStartBorderColor(),
        endBorderColor = chip.getEndBorderColor(),
        iconColor = chip.getIconColor(),
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FGChip(
    text: String,
    startBorderColor: Color = Red,
    endBorderColor: Color = RedOrange,
    iconColor: Color = Purple200,
    modifier: Modifier = Modifier
) {

    Chip(
        modifier = modifier
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(colors = listOf(startBorderColor, endBorderColor)),
                shape = RoundedCornerShape(50)
            )
            .height(36.dp),
        onClick = { /* Do something! */ },
        colors = ChipDefaults.chipColors(
            backgroundColor = Color.White,
            contentColor = iconColor
        ),
        leadingIcon = {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = "Localized description"
            )
        }
    ) {
        FGText(
            text = text,
            style = TextAlbertSans12,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun FGChipPreview() {
    FGChip(text = "My chip")
}


@Composable
fun FGChipSection(
    chips: List<Chip>
) {

    FlowRow(
        mainAxisAlignment = FlowMainAxisAlignment.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        chips.forEach { chip ->
            FGChip(
                chip = chip,
                modifier = Modifier.padding(end = 4.dp, bottom = 0.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun FGChipSectionPreview() {
    FGChipSection(chips = Chip.mockChips)
}





