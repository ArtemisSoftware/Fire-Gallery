package com.artemissoftware.common.composables.scaffold

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGCircularButton
import com.artemissoftware.common.composables.indicator.FGIndicator
import com.artemissoftware.common.composables.loading.FGLoading
import com.artemissoftware.common.composables.topbar.FGTopBar

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FGBottomSheetScaffold(
    isLoading: Boolean = false,
    sheetShape: Shape = MaterialTheme.shapes.large,
    sheetContent: @Composable ColumnScope.() -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    showTopBar: Boolean = false,
    title: String? = null,
    subtitle: String? = null,
    onNavigationClick: (() -> Unit) = {},
    topBarOptionComposable: (@Composable BoxScope.() -> Unit)? = null,
) {

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy
//        )
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(color.getBackgroundColor())
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShape = sheetShape,
            sheetPeekHeight =  42.dp,
            sheetContent = {

                BottomSheetContent(sheetContent = sheetContent)
            },
            content = content
        )

            FGTopBar(
                title = title,
                subTitle = subtitle,
                isVisible = showTopBar,
                onNavigationClick = onNavigationClick,
                optionComposable = topBarOptionComposable
            )

        FGLoading(isLoading = isLoading)
    }
}


@Composable
private fun ColumnScope.BottomSheetContent(
    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        FGIndicator()
    }
    sheetContent()
}



@Preview(showBackground = true)
@Composable
private fun FGBottomSheetScaffoldPreview() {
    FGBottomSheetScaffold(
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sheet")
                Text(text = "Sheet")
                Text(text = "Sheet")
                Text(text = "Sheet")
                Text(text = "Sheet")

            }
        },
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        },
        topBarOptionComposable = {
            FGCircularButton(
                imageVector = Icons.Default.KeyboardArrowLeft,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    )
}