package com.artemissoftware.data.firebase.remoteconfig.models

import com.artemissoftware.domain.util.SeasonType
import com.google.gson.annotations.SerializedName

data class SeasonRco(
    @SerializedName("backgroundColor")
    val backgroundColor: String,
    @SerializedName("chipColor")
    val chipColor: ChipColorRco,
    @SerializedName("seasonMessage")
    val seasonMessage: String,

    @SerializedName("spring")
    val spring: SeasonDetailRco,
    @SerializedName("summer")
    val summer: SeasonDetailRco,
    @SerializedName("autumn")
    val autumn: SeasonDetailRco,
    @SerializedName("winter")
    val winter: SeasonDetailRco
){

    //TODO tirar isto daqui
    fun getSeason(seasonType: SeasonType): SeasonDetailRco{
        return when(seasonType){
            SeasonType.SPRING -> spring
            SeasonType.SUMMER -> summer
            SeasonType.AUTUMN -> autumn
            SeasonType.WINTER -> winter
        }
    }


}
