package com.artemissoftware.firegallery.screens.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.Gallery
import com.artemissoftware.firegallery.screens.gallery.composables.GalleryCard
import com.artemissoftware.firegallery.screens.gallery.mappers.toUI
import com.artemissoftware.firegallery.ui.ManageUIEvents
import com.artemissoftware.firegallery.ui.UiEvent

@Composable
fun GalleryScreen(
    viewModel: GalleryViewModel
) {

    val state = viewModel.state.collectAsState()

    BuildGalleryScreen(state = state.value, events = viewModel::onTriggerEvent)
}

@Composable
private fun BuildGalleryScreen(
    state: GalleryState,
    events: ((GalleryEvents) -> Unit)? = null
) {

    FGScaffold(
        isLoading = state.isLoading
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(state.galleries) { gallery->

                GalleryCard(
                    gallery = gallery,
                    onClick = {
                        events?.invoke(GalleryEvents.GoToPictures(gallery.toUI()))
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GalleryScreenPreview() {

    val state = GalleryState(galleries = Gallery.galleryMockList)
    BuildGalleryScreen(state, events = {})
}
