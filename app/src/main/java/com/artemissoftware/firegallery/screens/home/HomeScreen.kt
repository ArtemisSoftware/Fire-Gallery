package com.artemissoftware.firegallery.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.navigation.mapper.toBottomBarItem
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.HomeDestinations
import com.artemissoftware.firegallery.navigation.HomeNavigationGraph
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations


@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    scaffoldState: FGScaffoldState
) {
    //"https://artemis-software.com/pictureId=AAOO"
    //navController.navigate(GalleryDestinations.PictureDetail.withArgs("AAOO"))

    FGScaffold(
        fgScaffoldState = scaffoldState,
        bottomBarItems = scaffoldState.bottomBarItems.value,
        navController = navController
    ) {

        try {
            //navController.navigate(Uri.parse("https://artemis-software.com/pictureId=" + "AAOO"))
        }
        catch (e: java.lang.NullPointerException){

        }

        HomeNavigationGraph(navController = navController, scaffoldState = scaffoldState)

        Button(
            onClick = {

                navController.navigate(Uri.parse("https://artemis-software.com/pictureId=" + "AAOO"))
                //navController.navigate("https://artemis-software.com/pictureId=AAOO")
                //--navController.navigate(GalleryDestinations.PictureDetail.withArgs("AAOO"))
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Navigate By DeepLink")
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    //HomeScreen()
}