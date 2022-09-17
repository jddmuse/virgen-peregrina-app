package com.virgen.peregrina.data.api.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor() {

    companion object {
        private const val TAG = "AuthService"
    }

    private val auth = Firebase.auth


    suspend fun signIn(email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "METHOD CALLED: signIn()")
            auth.signInWithEmailAndPassword(email, password).await()
        }

    suspend fun signUp(email: String, password: String): AuthResult =
        withContext(Dispatchers.IO) {
            Log.d(TAG, "METHOD CALLED: signUp()")
            auth.createUserWithEmailAndPassword(email, password).await()
        }

}