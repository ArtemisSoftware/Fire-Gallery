package com.artemissoftware.common.models

import kotlin.math.absoluteValue

enum class SwipeResult(
    val direction : Int
) {
    ACCEPT(1), REJECT(-1);

    companion object {
        fun getFromOffset(offset : Float) : SwipeResult{
            return if(isAcceptGesture(offset)) ACCEPT else REJECT;
        }

        fun isAcceptGesture(offset: Float) = offset < 0 && ACCEPT.direction < 0 || offset > 0 && ACCEPT.direction > 0

        fun isDismissGesture(offset: Float) = !isAcceptGesture(offset)
    }
}