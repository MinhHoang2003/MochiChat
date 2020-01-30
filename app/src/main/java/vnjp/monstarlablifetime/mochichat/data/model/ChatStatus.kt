package vnjp.monstarlablifetime.mochichat.data.model

import com.google.firebase.database.PropertyName

data class ChatStatus(
    @PropertyName("has_seen")
    var has_seen: Boolean? = false,
    var status: String? = null
)