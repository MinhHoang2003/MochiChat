package vnjp.monstarlablifetime.mochichat.data.model

import com.google.firebase.database.PropertyName

data class Content(
    var content: String? = null,
    @PropertyName("content_type")
    var content_type: String? = ContentType.TEXT,
    var date: String? = null,
    var from: String? = null,
    @PropertyName("has_seen")
    var has_seen: ArrayList<String>? = null
)

object ContentType {
    const val TEXT: String = "text"
    const val IMAGE: String = "image"
    const val AUDIO: String = "audio"
    const val VIDEO: String = "video"

}