package com.artemissoftware.firegallery.screens.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artemissoftware.common.composables.button.FGButton
import com.artemissoftware.common.composables.button.FGOutlinedButton
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.text.FGText
import com.artemissoftware.common.theme.FGStyle.TextAlbertSansBold28
import com.artemissoftware.domain.models.profile.Profile
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.profile.composables.ProfileOption


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {

    val state = viewModel.state.collectAsState().value

    BuildProfileScreen(
        state = state,
        events = viewModel::onTriggerEvent
    )
}

@Composable
private fun BuildProfileScreen(
    state: ProfileState,
    events: ((ProfileEvents) -> Unit)? = null,
) {

    val context = LocalContext.current

    val appConfig = state.profile?.appConfig
    val user = state.profile?.user

    FGScaffold(
        isLoading = state.isLoading
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FGText(
                modifier = Modifier.padding(top = 16.dp),
                style = TextAlbertSansBold28,
                text = stringResource(R.string.profile)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                user?.let {

                    it.name?.let{
                        item {

                            ProfileOption(
                                icon = Icons.Filled.AccountBox,
                                title = it,
                                description = stringResource(R.string.user_name),
                                onClick = {}

                            )
                        }
                    }
                    item {

                        ProfileOption(
                            icon = Icons.Filled.Favorite,
                            title = it.favorites.size.toString(),
                            description = stringResource(R.string.number_favorite_pictures),
                            onClick = {
                                events?.invoke(ProfileEvents.GoToFavorites)
                            }
                        )
                    }
                }

                appConfig?.let {

                    item {

                        ProfileOption(
                            icon = Icons.Filled.Notifications,
                            isChecked = it.notifications,
                            description = stringResource(R.string.allow_receive_push_notifications),
                            onCheck = {

                                events?.invoke(ProfileEvents.UpdateProfile(notificationsEnabled = it))
                            }
                        )

                    }

                    item {
                        ProfileOption(
                            icon = Icons.Filled.Token,
                            title = stringResource(R.string.firebase_token),
                            description = it.firebaseToken,
                            onClick = {

                                val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("token", it.firebaseToken)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, R.string.token_copied, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        )
                    }
                }

                item {

                    Row(modifier = Modifier.padding(top = 16.dp)) {

                        user?.let {

                            FGButton(
                                modifier = Modifier.fillMaxWidth(),
                                text = stringResource(R.string.log_out),
                                onClick = {
                                    events?.invoke(ProfileEvents.LogOut)
                                }
                            )
                        } ?: kotlin.run {


                            FGOutlinedButton(
                                modifier = Modifier.weight(0.5F),
                                text = stringResource(R.string.log_in),
                                onClick = {
                                    events?.invoke(ProfileEvents.GoToLogin)
                                }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            FGButton(
                                modifier = Modifier.weight(0.5F),
                                text = stringResource(R.string.register),
                                onClick = {
                                    events?.invoke(ProfileEvents.GoToRegister)
                                }
                            )
                        }
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun BuildProfileScreenPreview() {

    BuildProfileScreen(state = ProfileState(profile = Profile.mockProfile))
}