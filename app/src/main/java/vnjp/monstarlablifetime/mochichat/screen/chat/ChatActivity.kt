package vnjp.monstarlablifetime.mochichat.screen.chat

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import vnjp.monstarlablifetime.mochichat.R

class ChatActivity : AppCompatActivity() {
    private lateinit var edtEnterMessage: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initView()
    }

    private fun initView() {
        edtEnterMessage = findViewById(R.id.edtEnterMessage)
        edtEnterMessage.requestFocus()
    }
}
