package com.artemissoftware.data.firebase.remoteconfig.models


import com.google.gson.annotations.SerializedName

data class SeasonDetailRco(
    @SerializedName("chipColor")
    val chipColor: ChipColorRco
)