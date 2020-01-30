package vnjp.monstarlablifetime.mochichat.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vnjp.monstarlablifetime.mochichat.data.model.Chat
import vnjp.monstarlablifetime.mochichat.data.model.Status


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
    fun listeningChatStatus(
        chatKey: String,
        onDataLoad: (Boolean) -> Unit,
        onError: (String) -> Unit
    ) {
        FirebaseDatabase.getInstance().getReference("chats").child(chatKey)
            .child("paticipants")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    onError.invoke(p0.message)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    for (snap in p0.children) {
                        val value = snap.getValue(Boolean::class.java)
                        if (value == true) {
                            onDataLoad.invoke(true)
                            return
                        }
                    }
                    onDataLoad.invoke(false)
                }

            })
    }

    fun getStatus(
        chatKey: String,
        users: HashMap<String, Boolean>
    ): Boolean {
        for ((key, v) in users) {
            FirebaseDatabase.getInstance().getReference("users").child(key)
                .child("status")
                .addValueEventListener(object : ValueEventListener {

                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        val status = p0.getValue(String::class.java)
                        if (status == Status.ONLINE) {
                            setUserStatusOfChat(chatKey, key, true)
                        } else setUserStatusOfChat(chatKey, key, false)
                    }
                })
        }
        return false
    }

    private fun setUserStatusOfChat(chatKey: String, userKey: String, value: Boolean) {
        FirebaseDatabase.getInstance().getReference("chats").child(chatKey)
            .child("paticipants")
            .child(userKey)
            .setValue(value)

    }
}