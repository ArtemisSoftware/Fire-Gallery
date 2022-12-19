package com.artemissoftware.data.firebase

import com.artemissoftware.data.errors.FireGalleryException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException

object HandleFirebase {

    suspend inline fun <T, reified OUT_TYPE> safeApiCall(callFunction: suspend () -> T): T {

        return try{

           val apiResponse = callFunction.invoke()

            if(apiResponse is OUT_TYPE){
                return apiResponse
            }

            with(apiResponse as List<DocumentSnapshot>) {

                this.map { document ->
                    if(document.toObject<OUT_TYPE>() == null)
                        throw FireGalleryException(
                            message = "Invalid Type",
                            description = "Type provided for conversion is invalid ${apiResponse!!::class.java}"
                        )
                }
            }

            apiResponse

        }
        catch (exCast: ClassCastException){
            throw FireGalleryException(
                message = "Invalid Type",
                description = "Type provided for conversion is invalid"
            )
        }
        catch (ex: FirebaseException){

            when(ex){
                is FirebaseFirestoreException -> {

                    throw FireGalleryException(
                        code = ex.code.value(),
                        message = ex.code.name,
                        description = ex.message
                    )
                }
                is FirebaseAuthInvalidUserException -> {

                    throw FireGalleryException(
                        message = ex.errorCode,
                        description = ex.message
                    )
                }

                is FirebaseAuthUserCollisionException ->{
                    throw FireGalleryException(
                        message = ex.errorCode,
                        description = ex.message
                    )
                }

                is FirebaseRemoteConfigException ->{
                    throw FireGalleryException(
                        message = "",
                        description = ex.message
                    )
                }

                is FirebaseNetworkException ->{
                    throw FireGalleryException(
                        message = "",
                        description = ex.message
                    )
                }
                else -> throw ex//emitter.onError(ErrorType.UNKNOWN)
            }
        }
    }




}