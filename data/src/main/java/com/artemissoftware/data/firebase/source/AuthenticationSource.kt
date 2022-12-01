package com.artemissoftware.data.firebase.source

import com.artemissoftware.data.errors.FireGalleryException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthenticationSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun loginUser(email: String, password: String) : FirebaseUser {

        return suspendCoroutine { continuation ->
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { task->
                    task.user?.let {
                        continuation.resume(it)
                    } ?: kotlin.run {
                        continuation.resumeWithException(FireGalleryException(message = "Error authenticating the user"))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun registerUser(email: String, password: String, username: String) : FirebaseUser {

        return suspendCoroutine { continuation ->
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { task->
                    task.user?.let {
                        updateUsername(firebaseUser = it, username = username)

                        continuation.resume(it)
                    } ?: kotlin.run {
                        continuation.resumeWithException(FireGalleryException(message = "Error authenticating the user"))
                    }
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    private fun updateUsername(firebaseUser: FirebaseUser, username: String){

        val profileUpdates = UserProfileChangeRequest
            .Builder()
            .setDisplayName(username).build()

        firebaseUser.updateProfile(profileUpdates)
    }

    @ExperimentalCoroutinesApi
    fun getUser(): Flow<FirebaseUser?> =
        callbackFlow {
            val authStateListener = FirebaseAuth.AuthStateListener {
                trySendBlocking(it.currentUser)
            }
            firebaseAuth.addAuthStateListener(authStateListener)
            awaitClose {
                firebaseAuth.removeAuthStateListener(authStateListener)
            }
        }

    fun isLoggedIn() : Boolean {
        return firebaseAuth.currentUser != null
    }

    fun logOut() {
        return firebaseAuth.signOut()
    }
}

