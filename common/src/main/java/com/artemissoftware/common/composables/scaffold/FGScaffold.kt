package com.artemissoftware.common.composables.scaffold

import android.annotation.SuppressLint
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.R
import com.artemissoftware.common.composables.dialog.FGDialog
import com.artemissoftware.common.composables.loading.FGLoading
import com.artemissoftware.common.composables.navigation.FGBottomNavigationBar
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.common.composables.topbar.FGTopBar
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@Composable
fun FGScaffold(
    modifier: Modifier = Modifier/*.statusBarsPadding()*/,
//    color: EDPAppBarColor = EDPAppBarColor.GREY,
//    @DrawableRes navigationIconId: Int? = R.drawable.ic_arrow_left,
//    navigationText: String? = null,
//    onNavigationClick: (() -> Unit) = {},
//    @DrawableRes optionIconId: Int? = null,
//    optionText: String? = null,
//    onOptionClick: (() -> Unit) = {},
//    optionComposable: (@Composable BoxScope.() -> Unit)? = null,
//    isSearchAppBar: Boolean = false,
//    searchValue: String = "",
//    onSearchValue: (String) -> Unit = {},
    fgScaffoldState: FGScaffoldState? = null,
    isLoading: Boolean = false,
    @RawRes lottieId: Int = R.raw.gallery_photo,
    showTopBar: Boolean = false,
    title: String? = null,
    subtitle: String? = null,
    onNavigationClick: (() -> Unit)? = null,
    topBarOptionComposable: (@Composable BoxScope.() -> Unit)? = null,
    navController: NavHostController? = null,
    bottomBarItems: List<BottomBarItem> = emptyList(),
    content: @Composable (PaddingValues) -> Unit,
) {

    val bottomBarHeight = 64.dp
    val bottomBarHeightPx = with(LocalDensity.current) {
        bottomBarHeight.roundToPx().toFloat()
    }
    val bottomBarOffsetHeightPx = remember { mutableStateOf(0f) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y

                val newOffset = bottomBarOffsetHeightPx.value + delta
                bottomBarOffsetHeightPx.value = newOffset.coerceIn(-bottomBarHeightPx, 0f)


                return Offset.Zero
            }
        }
    }



    Box(
        modifier = Modifier.nestedScroll(nestedScrollConnection)
            .fillMaxSize()
//            .background(color.getBackgroundColor())
    ) {
        var scaffoldModifier = modifier
            .fillMaxSize()
            .background(Color.White)


        Scaffold(
            modifier = scaffoldModifier,
            bottomBar = {

                FGBottomNavigationBar(
                    modifier = Modifier
                        .height(bottomBarHeight)
                        .offset {
                            IntOffset(x = 0, y = -bottomBarOffsetHeightPx.value.roundToInt())
                        },
                    items = bottomBarItems,
                    navController = navController,
                    fgScaffoldState = fgScaffoldState
                )

            },
            content = content
        )

        onNavigationClick?.let {
            FGTopBar(
                title = title,
                subTitle = subtitle,
                isVisible = showTopBar,
                onNavigationClick = onNavigationClick!!,
                optionComposable = topBarOptionComposable
            )
        }

        FGLoading(isLoading = isLoading, lottieId = lottieId)

        fgScaffoldState?.let { FGDialog(fgScaffoldState = it) }

    }
}



@Preview(showBackground = true)
@Composable
private fun FGScaffoldPreview() {

    class TestDestination: BaseDestinations(route = "Create")

    val list = listOf(
        BottomBarItem(R.string.confirm, Icons.Filled.Create, Icons.Outlined.Create, TestDestination()),
        BottomBarItem(R.string.confirm, Icons.Filled.Person, Icons.Outlined.Person, TestDestination())
    )

    FGScaffold(
        navController = rememberNavController(),
        bottomBarItems = list,
        content = {

            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Text")
            }

        }
    )
}
