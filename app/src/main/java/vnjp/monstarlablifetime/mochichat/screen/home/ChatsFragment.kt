package vnjp.monstarlablifetime.mochichat.screen.home


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_chats.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.model.ChatMappingItem
import vnjp.monstarlablifetime.mochichat.data.model.OptionalButton
import vnjp.monstarlablifetime.mochichat.data.model.OptionalClickListener
import vnjp.monstarlablifetime.mochichat.screen.SwipeHelper
import vnjp.monstarlablifetime.mochichat.screen.chat.ChatDetailActivity

/**
 * A simple [Fragment] subclass.
 */
class ChatsFragment : Fragment() {
    companion object {
        var TAG: String = ChatsFragment::class.java.simpleName
        const val KEY_ITEM = "KEY_ITEM"
    }

    private lateinit var recyclerViewRecentChats: RecyclerView
    private lateinit var chatsAdapter: ChatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        recyclerViewRecentChats = view.findViewById(R.id.recyclerChatHistory)
        val query = FirebaseDatabase.getInstance().getReference("chats_mapping")
            .child("test01")
        val optional: FirebaseRecyclerOptions<Boolean> =
            FirebaseRecyclerOptions.Builder<Boolean>()
                .setQuery(query, Boolean::class.java).build()
        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d(TAG, p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val chat = p0.getValue(ChatMappingItem::class.java)
                Log.d(TAG, chat?.chat_status?.size.toString())
            }
        })
        recyclerViewRecentChats.layoutManager = LinearLayoutManager(context)
        chatsAdapter = ChatsAdapter(optional, context!!)

        val swipeHelper = object : SwipeHelper(context!!, recyclerViewRecentChats, 200) {
            override fun initOptionalButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<OptionalButton>
            ) {
                buffer.add(
                    OptionalButton(
                        context!!,
                        "Delete",
                        30,
                        R.drawable.ic_conversation,
                        Color.parseColor("#d0021b"),
                        object : OptionalClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(
                                    context!!,
                                    "on click delete $pos",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    )
                )
                buffer.add(
                    OptionalButton(
                        context!!,
                        "Has Seen",
                        30,
                        R.drawable.ic_read_mes,
                        Color.parseColor("#f5a623"),
                        object : OptionalClickListener {
                            override fun onClick(pos: Int) {
                                Toast.makeText(context!!, "on click check $pos", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    )
                )
            }

        }

        recyclerViewRecentChats.adapter = chatsAdapter
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        chatsAdapter.startListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        buttonShowChatHistory.setOnClickListener {
            if (expandedLayoutChatHistory.isExpanded) expandedLayoutChatHistory.collapse()
            else expandedLayoutChatHistory.expand()
        }
        buttonFriends.setOnClickListener {
            if (expandedLayoutFriend.isExpanded) expandedLayoutFriend.collapse()
            else expandedLayoutFriend.expand()
        }

        chatsAdapter.onClick = { key ->
            val intent = Intent(this.requireContext(), ChatDetailActivity::class.java)
            intent.putExtra(KEY_ITEM, key)
            startActivity(intent)
        }
    }


    override fun onStop() {
        super.onStop()
        chatsAdapter.stopListening()
    }


}
