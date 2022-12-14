package com.artemissoftware.firegallery.screens.tindergallery

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.models.SwipeResult
import com.artemissoftware.domain.Resource
import com.artemissoftware.domain.models.Picture
import com.artemissoftware.domain.usecases.favorite.UpdateFavoriteUseCase
import com.artemissoftware.domain.usecases.setup.GetSeasonConfigUseCase
import com.artemissoftware.domain.usecases.tinder.GetPicturesForTinderUseCase
import com.artemissoftware.domain.util.SeasonType
import com.artemissoftware.firegallery.navigation.NavigationArguments
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.lang.Integer.max
import javax.inject.Inject

@HiltViewModel
class TinderGalleryViewModel @Inject constructor(
    private val getPicturesForTinderUseCase: GetPicturesForTinderUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getSeasonConfigUseCase: GetSeasonConfigUseCase,
    private val savedStateHandle: SavedStateHandle
) : FGBaseEventViewModel<TinderGalleryEvents>() {


    private val _state: MutableState<TinderGalleyState> = mutableStateOf(TinderGalleyState())
    val state: State<TinderGalleyState> = _state

    private val blackListedPictures = mutableListOf<Picture>()


    init {
        onTriggerEvent(TinderGalleryEvents.FetchMorePictures)
    }

    override fun onTriggerEvent(event: TinderGalleryEvents) {
        when(event) {
            is TinderGalleryEvents.FetchMorePictures -> {
                fetchMorePictures()
            }

            is TinderGalleryEvents.GoToNextPicture -> {
                when(event.action) {
                    SwipeResult.ACCEPT -> {
                        _state.value.getCurrentPicture()?.let { picture ->
                            saveFavorite(picture)
                        }?: run {
                            calculateNextMove()
                        }
                    }
                    else -> {
                        _state.value.getCurrentPicture()?.let {
                            blackListedPictures.add(it)
                        }
                        calculateNextMove()
                    }
                }
            }
        }
    }



    private fun fetchMorePictures() {
        getPicturesForTinderUseCase(blackListedPictures = blackListedPictures).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    val resultData = result.data?: emptyList()
                    _state.value = _state.value.copy(
                        isLoading = false,
                        pictures = resultData,
                        currentIndex = max(0, resultData.size - 1)
                    )

                    getSeasonConfig()
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveFavorite(picture : Picture) {
        viewModelScope.launch {
            updateFavoriteUseCase.invoke(pictureId = picture.id, isFavorite = true)
            calculateNextMove()
        }
    }


    private fun calculateNextMove() {
        if(_state.value.isInTheLastPicture()) {
            _state.value = _state.value.copy(
                pictures = emptyList()
            )
        } else {
            val nextIndex = max(_state.value.currentIndex - 1, 0)

            _state.value = _state.value.copy(
                currentIndex = nextIndex
            )
        }
    }


    private fun getSeasonConfig(){
        savedStateHandle.get<String>(NavigationArguments.SEASON)?.let {
            val message =  it.capitalize() + " season pictures"
            val seasonDetailConfig = getSeasonConfigUseCase(SeasonType.getType(it))

            _state.value = _state.value.copy(
                notificationMessage = message,
                seasonDetailConfig = seasonDetailConfig
            )
        }
    }
}