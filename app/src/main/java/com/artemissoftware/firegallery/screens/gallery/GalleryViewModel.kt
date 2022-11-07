package com.artemissoftware.firegallery.screens.gallery

import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetGalleriesUseCase
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getGalleriesUseCase: GetGalleriesUseCase
): FGBaseEventViewModel<GalleryEvents>() {

    private val _state = MutableStateFlow(GalleryState())
    val state: StateFlow<GalleryState> = _state

    init {
        onTriggerEvent(GalleryEvents.GetGalleries)
    }

    override fun onTriggerEvent(event: GalleryEvents) {
        when(event){

            is GalleryEvents.GetGalleries -> {
                getGetGalleries()
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

                    _uiEvent.emit(
                        UiEvent.ShowErrorDialog(
                            title = "Gallery error",
                            message = result.message ?: "Unknown error"
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