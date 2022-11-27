package com.artemissoftware.firegallery.screens.pictures

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetUserUseCase
import com.artemissoftware.domain.usecases.pictures.GetPicturesUseCase
import com.artemissoftware.domain.usecases.pictures.GetPicturesUseCase.Companion.INEXISTENT_GALLERY
import com.artemissoftware.domain.usecases.pictures.GetPicturesUseCase.Companion.NO_PICTURES_AVAILABLE
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.screens.gallery.models.GalleryUI
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val getPicturesUseCase_: GetPicturesUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
): FGBaseEventViewModel<PictureEvents>() {

    private val _state: MutableStateFlow<PictureState> = MutableStateFlow(PictureState())
    val state: StateFlow<PictureState> = _state

    private var gallery: GalleryUI?

    init {

        gallery = savedStateHandle.get<GalleryUI>(NavigationArguments.GALLERY_ID)

        gallery?.let {
            getUser()
            onTriggerEvent(PictureEvents.GetPictures(galleryId = it.id))
        } ?: run{
            showDialog(message = INEXISTENT_GALLERY)
        }

    }

    override fun onTriggerEvent(event: PictureEvents) {
        when(event){

            is PictureEvents.GetPictures -> {
                getPictures(event.galleryId)
            }
            is PictureEvents.GoToPictureDetail -> {
                sendUiEvent(UiEvent.Navigate(GalleryDestinations.PictureDetail.withArgs(event.pictureId)))
            }
            PictureEvents.PopBackStack -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun getUser(){
        viewModelScope.launch {
            getUserUseCase.invoke().collectLatest { result ->
                _state.value = _state.value.copy(
                    isAuthenticated = result != null,
                )
            }
        }
    }

    private fun getPictures(galleryId : Int){

        getPicturesUseCase_.invoke(galleryId = galleryId).onEach { result ->

            when(result) {
                is Resource.Success -> {

                    _state.value = _state.value.copy(
                        galleryName = gallery?.name,
                        pictures = result.data ?: emptyList(),
                        isLoading = false,
                        showOptions = true
                    )
                }
                is Resource.Error -> {

                    _state.value = state.value.copy(
                        pictures = result.data ?: emptyList(),
                        isLoading = false,
                        showOptions = false
                    )

                    result.message?.let { showDialog(it, gallery?.let { " for ${it.name} gallery" } ?: "") }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
            }

        }.launchIn(viewModelScope)

    }


    private fun showDialog(message: String, messageToAppend: String = ""){

        when(message){

            NO_PICTURES_AVAILABLE ->{

                sendUiEvent(UiEvent.ShowDialog(
                        DialogType.Info(
                            title = "Pictures",
                            description = message + messageToAppend,
                            dialogOptions = DialogOptions(
                                confirmationTextId = R.string.accept,
                                confirmation = {
                                    sendUiEvent(UiEvent.PopBackStack)
                                }
                            )
                        )
                    )
                )
            }
            INEXISTENT_GALLERY ->{

                sendUiEvent(UiEvent.ShowDialog(
                        DialogType.Error(
                            title = "Pictures",
                            description = message,
                            dialogOptions = DialogOptions(
                                confirmationTextId = R.string.accept,
                                confirmation = {
                                    sendUiEvent(UiEvent.PopBackStack)
                                }
                            )
                        )
                    )
                )
            }
        }
    }


}
