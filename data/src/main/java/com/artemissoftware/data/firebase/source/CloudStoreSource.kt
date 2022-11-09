package com.artemissoftware.data.firebase.source

import com.artemissoftware.data.firebase.FireStoreCollection
import com.artemissoftware.data.firebase.FireStoreDocumentField
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CloudStoreSource @Inject constructor(private val firebaseFirestore: FirebaseFirestore){

    suspend fun getDocuments(collectionName: String): List<DocumentSnapshot> {

        return suspendCoroutine { continuation ->
            firebaseFirestore
                .collection(collectionName)
                .get()
                .addOnSuccessListener {
                    continuation.resume(it.documents)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun getPictures(galleryId: Int): List<DocumentSnapshot> {

        return suspendCoroutine { continuation ->
            firebaseFirestore
                .collection(FireStoreCollection.PICTURES)
                .whereEqualTo(FireStoreDocumentField.GALLERY_ID, galleryId)
                .get()
                .addOnSuccessListener {
                    continuation.resume(it.documents)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun getPicturesForTinder(numberOfImages: Int, favoriteImages: List<String>, blackListedPictureIds : List<String>): List<DocumentSnapshot> {

        return suspendCoroutine { continuation ->
            var query = firebaseFirestore
                .collection(FireStoreCollection.PICTURES)

                .limit(numberOfImages.toLong())

            if(favoriteImages.isNotEmpty()) {
                query = query.whereNotIn(FireStoreDocumentField.ID, favoriteImages)
            }

            if(blackListedPictureIds.isNotEmpty()) {
                query = query.whereNotIn(FireStoreDocumentField.ID, blackListedPictureIds)
            }

            query.get()
                .addOnSuccessListener {
                    continuation.resume(it.documents)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun getDocumentItems(collectionName: String, documentField: String, id: Object): List<DocumentSnapshot> {

        return suspendCoroutine { continuation ->
            firebaseFirestore
                .collection(collectionName)
                .whereEqualTo(documentField, id)
                .get()
                .addOnSuccessListener {
                    continuation.resume(it.documents)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun getDocumentItems(collectionName: String, documentField: String, id: List<String>): List<DocumentSnapshot> {

        return suspendCoroutine { continuation ->
            firebaseFirestore
                .collection(collectionName)
                .whereIn(documentField, id)
                .get()
                .addOnSuccessListener {
                    continuation.resume(it.documents)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }
}