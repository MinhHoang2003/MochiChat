package vnjp.monstarlablifetime.mochichat.data.repository

class UserRepositoryImp : UserRepository{
    override fun login(
        email: String,
        password: String,
        onResponseLogin: (isComplete: Boolean, message: String) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun register(
        email: String,
        password: String,
        onResponseRegister: (isComplete: Boolean, message: String) -> Unit
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}