package com.artemissoftware.firegallery.screens.favorites

import androidx.lifecycle.viewModelScope
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.usecases.GetUserUseCase
import com.artemissoftware.domain.usecases.favorite.GetFavoritePicturesUseCase
import com.artemissoftware.domain.usecases.favorite.GetFavoritePicturesUseCase.Companion.NO_FAVORITE_PICTURES_AVAILABLE
import com.artemissoftware.domain.usecases.favorite.UpdateFavoriteUseCase
import com.artemissoftware.firegallery.screens.pictures.PictureState
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritePicturesUseCase: GetFavoritePicturesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
): FGBaseEventViewModel<FavoriteEvents>() {

    private val _state = MutableStateFlow(PictureState())
    val state: StateFlow<PictureState> = _state

    init {
        onTriggerEvent(FavoriteEvents.GetUser)
        onTriggerEvent(FavoriteEvents.GetFavorites)
    }

    override fun onTriggerEvent(event: FavoriteEvents) {
        when(event){

            is FavoriteEvents.GetUser -> {
                getUser()
            }
            is FavoriteEvents.GetFavorites -> {
                getFavorites()
            }
            is FavoriteEvents.Remove -> {
                remove(event.pictureId)
            }
        }
    }

    private fun getUser(){

        viewModelScope.launch {

            getUserUseCase.invoke().collectLatest { result ->
                _state.value = _state.value.copy(
                    favorites = result?.favorites?: emptyList()
                )
            }
        }
    }


    private fun remove(pictureId: String) {
        updateFavoriteUseCase.invoke(pictureId = pictureId, isFavorite = false)
            .onEach {}
            .launchIn(viewModelScope)
    }


    private fun getFavorites(){

        viewModelScope.launch {

            _state.value = _state.value.copy(
                isLoading = true
            )

            getFavoritePicturesUseCase.invoke().collectLatest { result ->

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
                    else->{}
                }
            }
        }
    }


    private suspend fun showDialog(message: String){

        when(message){

            NO_FAVORITE_PICTURES_AVAILABLE ->{

                _uiEvent.emit(
                    UiEvent.ShowInfoDialog(
                        title = "Favorites",
                        message = NO_FAVORITE_PICTURES_AVAILABLE
                    )
                )
            }
        }
    }

}
