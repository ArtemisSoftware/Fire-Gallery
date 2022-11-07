package com.artemissoftware.firegallery.screens.picturedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetPictureDetailUseCase
import com.artemissoftware.domain.usecases.GetUserUseCase
import com.artemissoftware.domain.usecases.favorite.UpdateFavoriteUseCase
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureDetailViewModel @Inject constructor(
    private val getPictureDetailUseCase: GetPictureDetailUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getUserUseCase: GetUserUseCase,
    savedStateHandle: SavedStateHandle
): FGBaseEventViewModel<PictureDetailEvents>(){

    private val _state: MutableStateFlow<PictureDetailState> = MutableStateFlow(PictureDetailState())
    val state: StateFlow<PictureDetailState> = _state

    private val pictureId: String

    init {
        pictureId = savedStateHandle.get<String>(NavigationArguments.PICTURE_ID).orEmpty()
        onTriggerEvent(PictureDetailEvents.GetPicture(pictureId))
        onTriggerEvent(PictureDetailEvents.GetUser)
    }

    override fun onTriggerEvent(event: PictureDetailEvents) {

        when(event){
            is PictureDetailEvents.GetPicture -> {
                getPicture(event.id)
            }

            is PictureDetailEvents.GetUser -> {
                getUser()
            }
            is PictureDetailEvents.FavoritePicture -> {
                saveFavorite(pictureId = event.id, isFavorite = event.isFavorite)
            }
        }
    }

    private fun getUser(){

        viewModelScope.launch {

            getUserUseCase.invoke().collectLatest { result ->

                _state.value = _state.value.copy(
                    isFavorite = result?.favorites?.contains(pictureId) ?: false,
                )
            }
        }
    }


    private fun saveFavorite(pictureId: String, isFavorite: Boolean) {

        updateFavoriteUseCase(pictureId = pictureId, isFavorite = isFavorite)
            .onEach {

        }.launchIn(viewModelScope)
    }


    private fun getPicture(pictureId: String){

        getPictureDetailUseCase(pictureId).onEach { result->

            when(result) {
                is Resource.Success -> {

                    _state.value = _state.value.copy(
                        picture = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {

                    _state.value = state.value.copy(
                        isLoading = false
                    )

                    result.message?.let { showDialog(it) }
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                else ->{}
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun showDialog(message: String){

        when(message){

            GetPictureDetailUseCase.PICTURE_UNAVAILABLE ->{

                _uiEvent.emit(
                    UiEvent.ShowInfoDialog(
                        title = "Picture",
                        message = GetPictureDetailUseCase.PICTURE_UNAVAILABLE
                    )
                )
            }
        }
    }

}