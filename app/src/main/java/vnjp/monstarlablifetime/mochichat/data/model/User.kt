package vnjp.monstarlablifetime.mochichat.data.model

data class User(
    var image: String? = null,
    var name: String? = null,
    var status: String? = Status.OFFLINE
)

object Status {
    const val ONLINE: String = "online"
    const val OFFLINE: String = "offline"
    const val BUSY: String = "busy"
    const val UNREAD: String = "unread"

}