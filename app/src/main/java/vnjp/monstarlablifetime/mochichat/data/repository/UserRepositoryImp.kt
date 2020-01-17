package vnjp.monstarlablifetime.mochichat.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class UserRepositoryImp(
    private val userDatabaseReference: DatabaseReference,
    private val firebaseAuth: FirebaseAuth
) : UserRepository {

    override fun login(
        email: String,
        password: String,
        onResponseLogin: (isComplete: Boolean, message: String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResponseLogin.invoke(it.isSuccessful, "Login successfully")
                } else {
                    it.exception?.message?.let { mess ->
                        onResponseLogin.invoke(
                            it.isSuccessful,
                            mess
                        )
                    }
                }
            }
    }

    override fun register(
        email: String,
        password: String,
        onResponseRegister: (isComplete: Boolean, message: String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onResponseRegister.invoke(it.isSuccessful, "Register successfully")
                } else {
                    it.exception?.message?.let { mess ->
                        onResponseRegister.invoke(
                            it.isSuccessful,
                            mess
                        )
                    }
                }
            }
    }
}