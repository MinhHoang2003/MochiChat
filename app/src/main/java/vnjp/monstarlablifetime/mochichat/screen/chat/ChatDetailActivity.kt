package vnjp.monstarlablifetime.mochichat.screen.chat

import android.os.Bundle
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
                Log.d("items", "on error : " + p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                Log.d("items", "on data change : " + p0.childrenCount)
                if (chatDetailAdapter.itemCount - 1 > 0) recycleView.smoothScrollToPosition(
                    chatDetailAdapter.itemCount - 1
                )
            }
        })
        chatDetailAdapter.startListening()

        recycleView.adapter = chatDetailAdapter
        chatDetailAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.d("items", chatDetailAdapter.snapshots.size.toString())
                if (chatDetailAdapter.snapshots.size - 1 > 0)
                    recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
            }
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        chatDetailAdapter.stopListening()

    }

    override fun onStop() {
        super.onStop()
    }

    override fun onClick(v: View?) {
        when (v) {
            imbSend -> {
                val content = Content(
                    edtEnterMessage.text.toString() + String(Character.toChars(0x1F60A)),
                    "text",
                    "13:53 13/01/2020",
                    "test01",
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
