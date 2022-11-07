package com.artemissoftware.firegallery.screens.pictures

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetPicturesUseCase
import com.artemissoftware.domain.usecases.GetPicturesUseCase.Companion.INEXISTENT_GALLERY
import com.artemissoftware.domain.usecases.GetPicturesUseCase.Companion.NO_PICTURES_AVAILABLE
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.screens.gallery.models.GalleryUI
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    savedStateHandle: SavedStateHandle
): FGBaseEventViewModel<PictureEvents>() {

    private val _state: MutableStateFlow<PictureState> = MutableStateFlow(PictureState())
    val state: StateFlow<PictureState> = _state

    private var gallery: GalleryUI?

    init {

        gallery = savedStateHandle.get<GalleryUI>(NavigationArguments.GALLERY_ID)

        gallery?.let {
            onTriggerEvent(PictureEvents.GetPictures(galleryId = it.id))
        } ?: run{
            onTriggerEvent(PictureEvents.ShowError(message = INEXISTENT_GALLERY))
        }

    }

    override fun onTriggerEvent(event: PictureEvents) {
        when(event){

            is PictureEvents.GetPictures -> {
                getPictures(event.galleryId)
            }
            is PictureEvents.ShowError -> {
                showError(event.message)
            }
        }
    }

    private fun getPictures(galleryId : Int){

        getPicturesUseCase.invoke(galleryId = galleryId).onEach { result ->

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


    private suspend fun showDialog(message: String, messageToAppend: String){

        when(message){

            NO_PICTURES_AVAILABLE ->{

                _uiEvent.emit(
                    UiEvent.ShowInfoDialog(
                        title = "Pictures",
                        message = message + messageToAppend
                    )
                )
            }
        }
    }

    private fun showError(message: String){
        viewModelScope.launch {
            _uiEvent.emit(
                UiEvent.ShowErrorDialog(
                    title = "Pictures",
                    message = message
                )
            )
        }
    }


}
