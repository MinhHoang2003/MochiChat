package vnjp.monstarlablifetime.mochichat.data.model

import com.google.firebase.database.PropertyName
import java.util.HashMap

data class ChatMappingItem(
    @PropertyName("chat_status")
    var chat_status: HashMap<String, ChatStatus>? = null,
    var image: String? = null,
    @PropertyName("last_message")
    var last_message: String? = null,
    var name: String? = null
)