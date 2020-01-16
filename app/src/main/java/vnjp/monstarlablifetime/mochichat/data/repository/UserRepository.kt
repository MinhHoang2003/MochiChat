package vnjp.monstarlablifetime.mochichat.data.repository

interface UserRepository {

    fun login(
        email: String,
        password: String,
        onResponseLogin: (isComplete: Boolean, message: String) -> Unit
    )

    fun register(
        email: String,
        password: String,
        onResponseRegister: (isComplete: Boolean, message: String) -> Unit
    )

}