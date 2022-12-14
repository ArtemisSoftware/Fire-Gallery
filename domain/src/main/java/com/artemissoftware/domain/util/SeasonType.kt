package com.artemissoftware.domain.util

enum class SeasonType(val description: String) {
    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter");

    companion object {
        fun getType(text: String) = values().find { it.description == text.toLowerCase() }
    }
}