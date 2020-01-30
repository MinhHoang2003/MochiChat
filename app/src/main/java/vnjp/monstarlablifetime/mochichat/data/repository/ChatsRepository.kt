package vnjp.monstarlablifetime.mochichat.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vnjp.monstarlablifetime.mochichat.data.model.Chat

class ChatsRepository {
    private val databaseReference = FirebaseDatabase.getInstance().getReference("chats")
    fun getChats(
        key: String,
        onDataLoad: (Chat) -> Unit,
        onFailure: (String) -> Unit
    ) {
        databaseReference.child(key).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("firebase", p0.message)
                onFailure.invoke(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("firebase", p0.key)
                val chat = p0.getValue(Chat::class.java)
                if (chat != null) {
                    onDataLoad.invoke(chat)
                    return
                }
                onFailure.invoke("Data is null")
            }

        })
    }
}