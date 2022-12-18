package com.artemissoftware.firegallery.di

import android.app.Application
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.artemissoftware.data.firebase.source.AuthenticationSource
import com.artemissoftware.data.firebase.source.CloudStoreSource
import com.artemissoftware.data.firebase.source.RemoteConfigSource
import com.artemissoftware.data.repositories.*
import com.artemissoftware.domain.repositories.*
import com.artemissoftware.firegallery.ui.FGScaffoldState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGalleryRepository(cloudStoreSource: CloudStoreSource): GalleryRepository {
        return GalleryRepositoryImpl(cloudStoreSource)
    }

    @Provides
    @Singleton
    fun provideAppSettingsDataStoreRepository(app: Application): AppSettingsDataStoreRepository {
        return AppSettingsDataStoreRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideProfileDataStoreRepository(app: Application): ProfileDataStoreRepository {
        return ProfileDataStoreRepositoryImpl(app)
    }

    @Provides
    @Singleton
    fun provideLocalNotificationsRepository(app: Application, notification : NotificationCompat.Builder, notificationManager: NotificationManagerCompat): LocalNotificationsRepository {
        return LocalNotificationsRepositoryImpl(notification, notificationManager, app)
    }

    @Provides
    @Singleton
    fun provideRemoteConfigRepository(remoteConfigSource: RemoteConfigSource): RemoteConfigRepository {
        return RemoteConfigRepositoryImpl(remoteConfigSource)
    }


    @Provides
    @Singleton
    fun provideAuthenticationRepository(authenticationSource: AuthenticationSource): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationSource)
    }

    @Provides
    @Singleton
    fun provideAppScaffold(): FGScaffoldState {
        return FGScaffoldState(null)
    }
}