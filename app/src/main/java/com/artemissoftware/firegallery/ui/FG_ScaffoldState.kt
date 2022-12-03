package com.artemissoftware.firegallery.ui

import androidx.compose.runtime.mutableStateOf
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.models.profile.User
import kotlinx.coroutines.CoroutineScope

class FG_ScaffoldState(scope: CoroutineScope?) : FGScaffoldState(scope) {

    var user = mutableStateOf<User?>(null)
        private set


    fun setUser(user: User?) {
        this.user.value = user
    }

    fun isLoggedIn() = this.user.value != null

}