package vnjp.monstarlablifetime.mochichat.screen.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.items_recent_chats.view.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.model.Status
import vnjp.monstarlablifetime.mochichat.data.repository.ChatsRepository

class ChatsAdapter(
    query: FirebaseRecyclerOptions<Boolean>,
    private val context: Context
) : FirebaseRecyclerAdapter<Boolean, ChatsAdapter.ChatViewHolder>(query) {

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemKey: String) {
            getItem(itemKey, itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items_recent_chats, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(p0: ChatViewHolder, p1: Int, p2: Boolean) {
        p0.bind(snapshots.getSnapshot(p1).key!!)
    }

    private fun checkStatus(users: ArrayList<String>, itemView: View): Boolean {
        users.forEach {
            FirebaseDatabase.getInstance().getReference("users").child(it)
                .child("status")
                .addValueEventListener(object : ValueEventListener {
                    var onlineCount = 0
                    var offlineCount = 0
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val status = p0.getValue(String::class.java)
                        if (status == Status.ONLINE &&
                            onlineCount < users.size &&
                            offlineCount > 0
                        ) {
                            if (onlineCount + offlineCount == users.size) {
                                onlineCount++
                                offlineCount--
                            } else onlineCount++

                        }
                        if (status == Status.ONLINE &&
                            offlineCount < users.size &&
                            onlineCount > 0 &&
                            onlineCount + offlineCount == users.size
                        ) {
                            if (onlineCount + offlineCount == users.size) {
                                onlineCount--
                                offlineCount++
                            } else offlineCount++
                        }
                        if (onlineCount > 0) {
                            itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_online)
                        } else itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_bussy)
                    }
                })
        }
        return false
    }

    fun getItem(key: String, itemView: View) {
        ChatsRepository().getChats(key,
            onDataLoad = {
                Glide.with(context).load(it.image).into(itemView.imageUser)
                it.paticipants?.let { it1 -> checkStatus(it1, itemView) }
//                itemView.textUserName.text = item.name
//                itemView.textLastMessage.text = item.last_message
//                if (item.chat_status?.let { checkStatus(it) }!!) {
//                    itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_online)
//                } else itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_bussy)
            }, onFailure = {

            })
    }
}