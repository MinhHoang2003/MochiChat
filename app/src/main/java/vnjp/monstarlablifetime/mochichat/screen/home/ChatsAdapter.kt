package vnjp.monstarlablifetime.mochichat.screen.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.items_recent_chats.view.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.repository.ChatsRepository

class ChatsAdapter(
    query: FirebaseRecyclerOptions<Boolean>,
    private val context: Context
) : FirebaseRecyclerAdapter<Boolean, ChatsAdapter.ChatViewHolder>(query) {
    var onClick: ((String) -> Unit)? = null

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemKey: String) {
            getItem(itemKey, itemView)
            itemView.setOnClickListener {
                onClick?.invoke(itemKey)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items_recent_chats, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(p0: ChatViewHolder, p1: Int, p2: Boolean) {
        p0.bind(snapshots.getSnapshot(p1).key!!)
    }


    fun getItem(key: String, itemView: View) {
        val chatsRepository = ChatsRepository()
        chatsRepository.getChats(key,
            onDataLoad = { chat ->
                Glide.with(context).load(chat.image).into(itemView.imageUser)
                chat.paticipants?.let { it1 -> chatsRepository.getStatus(key, it1) }
                Log.d("status", " $key from get to onchange")
                chatsRepository.listeningChatStatus(key,
                    onDataLoad = { value ->
                        Log.d("status", "on status in getItem $key : $value")
                        if (value) {
                            itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_online)
                        } else itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_bussy)
                    }, onError = {
                        Log.d("status", it)
                    })
//                itemView.textUserName.text = item.name
//                itemView.textLastMessage.text = item.last_message
//                if (item.chat_status?.let { checkStatus(it) }!!) {
//                    itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_online)
//                } else itemView.userStatus.setBackgroundResource(R.drawable.bg_user_status_bussy)
            }, onFailure = {

            })
    }
}
