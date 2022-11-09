package com.artemissoftware.firegallery.screens.gallery

import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetGalleriesUseCase
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getGalleriesUseCase: GetGalleriesUseCase
): FGBaseEventViewModel<GalleryEvents>() {

    private val _state = MutableStateFlow(GalleryState())
    val state: StateFlow<GalleryState> = _state

    init {
        getGetGalleries()
    }

    override fun onTriggerEvent(event: GalleryEvents) {
        when(event){

            is GalleryEvents.GoToPictures -> {
                sendUiEvent(UiEvent.Navigate(GalleryDestinations.Pictures.withCustomArgs(event.galleryUI)))
            }
        }
    }

    private fun getGetGalleries(){

        getGalleriesUseCase.invoke().onEach { result ->

            when(result) {
                is Resource.Success -> {

                    _state.value = _state.value.copy(
                        galleries = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        galleries = result.data ?: emptyList(),
                        isLoading = false
                    )

                    sendUiEvent(UiEvent.ShowDialog(
                            DialogType.Error(
                                title = "Gallery",
                                description = result.message ?: "Unknown error",
                                dialogOptions = DialogOptions(
                                    confirmationTextId = R.string.accept,
                                )
                            )
                        )
                    )
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }

        }.launchIn(viewModelScope)


    }
}