package com.artemissoftware.common.composables.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.text.FGText


@Composable
fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}


@Preview
@Composable
fun MeasureUnconstrainedViewWidthPreview() {

    MeasureUnconstrainedViewWidth(
        viewToMeasure = {
            Column(
                modifier = Modifier.background(color = Color.Red)

            ) {

                FGText(
                    modifier = Modifier.padding(8.dp),
                    text = "Winter season pictures"
                )
            }
        }
    ) { measuredWidth ->
        Text("Example of text = $measuredWidth")
    }

}
