package vnjp.monstarlablifetime.mochichat.data.model

import com.google.firebase.database.PropertyName
import java.util.*

data class Chat(
    @PropertyName("contents")
    var contents: HashMap<String, Content>? = null,
    @PropertyName("image")
    var image: String? = null,
    @PropertyName("paticipants")
    var paticipants: ArrayList<String>? = null
)