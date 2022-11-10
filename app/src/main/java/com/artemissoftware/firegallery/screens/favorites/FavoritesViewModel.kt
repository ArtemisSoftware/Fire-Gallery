package com.artemissoftware.firegallery.screens.favorites

import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetUserUseCase
import com.artemissoftware.domain.usecases.favorite.UpdateFavoriteUseCase
import com.artemissoftware.domain.usecases.pictures.GetPicturesUseCase
import com.artemissoftware.domain.usecases.pictures.GetPicturesUseCase.Companion.NO_FAVORITE_PICTURES_AVAILABLE
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.navigation.HomeDestinations
import com.artemissoftware.firegallery.navigation.graphs.GalleryDestinations
import com.artemissoftware.firegallery.screens.pictures.PictureState
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
): FGBaseEventViewModel<FavoriteEvents>() {

    private val _state = MutableStateFlow(PictureState())
    val state: StateFlow<PictureState> = _state

    init {
        getUser()
        getFavorites()
    }

    override fun onTriggerEvent(event: FavoriteEvents) {
        when(event){

            is FavoriteEvents.Remove -> {
                remove(event.pictureId)
            }
            is FavoriteEvents.GoToPictureDetail -> {
                sendUiEvent(UiEvent.Navigate(GalleryDestinations.PictureDetail.withArgs(event.pictureId)))
            }
        }
    }

    private fun getUser(){
        viewModelScope.launch {
            getUserUseCase.invoke().collectLatest { result ->
                _state.value = _state.value.copy(
                    favorites = result?.favorites?: emptyList()
                )

                if(state.value.favorites.isEmpty() && state.value.pictures.isNotEmpty()){
                    showDialog(NO_FAVORITE_PICTURES_AVAILABLE)
                }
            }
        }
    }


    private fun remove(pictureId: String) {

        if(state.value.favorites.size -1 <= 0){
            showDialog(NO_FAVORITE_PICTURES_AVAILABLE)
        }

        viewModelScope.launch {
            updateFavoriteUseCase.invoke(pictureId = pictureId, isFavorite = false)
        }
    }


    private fun getFavorites(){

        viewModelScope.launch {

            getPicturesUseCase.invoke().collectLatest { result ->

                when(result) {
                    is Resource.Success -> {

                        _state.value = _state.value.copy(
                            pictures = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {

                        _state.value = state.value.copy(
                            pictures = result.data ?: emptyList(),
                            isLoading = false
                        )

                        result.message?.let { showDialog(it) }
                    }
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }
                    else->{}
                }
            }
        }
    }


    private fun showDialog(message: String){

        when(message){

            NO_FAVORITE_PICTURES_AVAILABLE ->{

                sendUiEvent(UiEvent.ShowDialog(
                        DialogType.Info(
                            title = "Favorites",
                            description = NO_FAVORITE_PICTURES_AVAILABLE,
                            dialogOptions = DialogOptions(
                                confirmationTextId = R.string.accept,
                                confirmation = {
                                    sendUiEvent(UiEvent.ChangeCurrentPositionBottomBar(HomeDestinations.Gallery))
                                }
                            )
                        )
                    )
                )

            }
            else->{
                sendUiEvent(UiEvent.ShowDialog(
                        DialogType.Error(
                            title = "Favorites",
                            description = message,
                            dialogOptions = DialogOptions(
                                confirmationTextId = R.string.accept,
                                confirmation = {
                                    sendUiEvent(UiEvent.ChangeCurrentPositionBottomBar(HomeDestinations.Gallery))
                                }
                            )
                        )
                    )
                )
            }
        }
    }

}
