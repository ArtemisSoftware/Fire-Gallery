package com.artemissoftware.firegallery

import com.artemissoftware.firegallery.ui.FGBaseEvents

sealed class MainEvents: FGBaseEvents() {
    object ExecuteDeepLink: MainEvents()
}
