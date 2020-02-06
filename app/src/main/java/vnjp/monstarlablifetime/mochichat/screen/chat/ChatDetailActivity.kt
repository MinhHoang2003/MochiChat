package vnjp.monstarlablifetime.mochichat.screen.chat

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.ACTION
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_detail_chat.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.model.Content
import vnjp.monstarlablifetime.mochichat.screen.home.ChatsFragment
import vnjp.monstarlablifetime.mochichat.utils.Commons
import java.io.IOException
import java.util.*


class ChatDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtEnterMessage: EditText
    lateinit var chatDetailAdapter: ChatDetailAdapter
    lateinit var firebaseDatabase: FirebaseDatabase

    companion object {
        const val PERMISSION_REQUEST_CODE = 101
        const val PICK_IMAGE = 102
    }

    lateinit var databaseReference: DatabaseReference
    private var keyItem: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_chat)
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
        imbBack.setOnClickListener(this)
        imbCamera.setOnClickListener(this)
        imbGallery.setOnClickListener(this)
        imbDelete.setOnClickListener(this)
        edtEnterMessage.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
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
                if (chatDetailAdapter.itemCount - 1 > 0)
                    recycleView.smoothScrollToPosition(
                        chatDetailAdapter.itemCount - 1
                    )
                Log.d("items", chatDetailAdapter.itemCount.toString())
                val intent = Intent()
                intent.action = ACTION
//                localBroadcastManager.sendBroadcast(intent)
                //    recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
            }
        })
        chatDetailAdapter.startListening()

        recycleView.adapter = chatDetailAdapter
        chatDetailAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {

                Log.d("items", chatDetailAdapter.snapshots.size.toString())
                if (chatDetailAdapter.snapshots.size - 1 > 0)
                    recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
                //recycleView.smoothScrollToPosition(chatDetailAdapter.snapshots.size - 1)
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

    //    + String(Character.toChars(0x1F60A))
    override fun onClick(v: View?) {
        when (v) {
            imbSend -> {
                val content = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Content(
                        edtEnterMessage.text.toString(),
                        "text", Commons.getStringCurrentDateTime(Date()),
                        "test02",
                        arrayListOf("test01")
                    )
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
                if (edtEnterMessage.text.toString().isNotEmpty()) {
                    postChat(content)
                }
                edtEnterMessage.text = null
            }
            imbBack ->
                finish()

            imbCamera -> {
                checkPermission()
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CODE
                )

            }
            imbGallery -> {
                checkPermission()
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PICK_IMAGE
                )
            }
            imbDelete -> {
                contentImage.setVisibility(View.GONE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, PERMISSION_REQUEST_CODE)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
        if (requestCode == PICK_IMAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(intent, PICK_IMAGE)
            } else {
                Toast.makeText(this, "folder image permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PERMISSION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photo = data?.getExtras()?.get("data") as Bitmap
            imgImageSend.setImageBitmap(photo)
            contentImage.setVisibility(View.VISIBLE)
        }
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val selectedImage = data!!.data
            try {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(22))
                Glide.with(this.applicationContext).load(selectedImage).apply(requestOptions)
                    .into(imgImageSend)
                contentImage.setVisibility(View.VISIBLE)
            } catch (e: IOException) {
                Log.i("TAG", "Some exception $e")
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
