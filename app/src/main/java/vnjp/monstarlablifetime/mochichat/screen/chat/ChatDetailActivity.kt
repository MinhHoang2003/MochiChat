package vnjp.monstarlablifetime.mochichat.screen.chat

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.model.Content
import vnjp.monstarlablifetime.mochichat.screen.home.ChatsFragment

class ChatDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtEnterMessage: EditText
    lateinit var chatDetailAdapter: ChatDetailAdapter
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private var keyItem: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("chats")
        displayChat()
        intent.extras.let {
            keyItem = it?.getString(ChatsFragment.KEY_ITEM)
        }
        recycleView.layoutManager = LinearLayoutManager(this)
        initView()
        initEvent()
    }

    private fun initEvent() {
        imbSend.setOnClickListener(this)
    }

    private fun initView() {
        edtEnterMessage = findViewById(R.id.edtEnterMessage)
        edtEnterMessage.requestFocus()
    }

    private fun displayChat() {
        val query = databaseReference
            .child("chat01")
            .child("contents")
            .limitToLast(50)


        val firebaseRecyclerOptions: FirebaseRecyclerOptions<Content> =
            FirebaseRecyclerOptions.Builder<Content>().setQuery(query, Content::class.java).build()
        chatDetailAdapter = ChatDetailAdapter(firebaseRecyclerOptions, this)
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("items", chatDetailAdapter.itemCount.toString())
                val intent = Intent()
                intent.action = ACTION
//                localBroadcastManager.sendBroadcast(intent)
            //    recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
            }
        })
        chatDetailAdapter.startListening()
        chatDetailAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
            }
        })
        recycleView.adapter = chatDetailAdapter
    }

    override fun onStop() {
        super.onStop()
        chatDetailAdapter.stopListening()
    }

    override fun onClick(v: View?) {
        when (v) {
            imbSend -> {
                val content = Content(
                    edtEnterMessage.text.toString() + String(Character.toChars(0x1F60A)),
                    "text",
                    "13:53 13/01/2020",
                    "test02",
                    arrayListOf("test01")
                )
                postChat(content)
            }
        }
    }

    private fun postChat(content: Content) {
        FirebaseDatabase.getInstance()
            .getReference("chats")
            .child("chat01")
            .child("contents")
            .push()
            .setValue(content)
            .addOnCompleteListener {
                Log.d("firebase", "Send content successful")
            }.addOnCanceledListener {
                Log.d("firebasae", "")
            }
    }

}
