package vnjp.monstarlablifetime.mochichat.screen.authenication.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.base.BaseActivity
import vnjp.monstarlablifetime.mochichat.screen.chat.ChatActivity

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var btnLogin: Button
    private lateinit var tvSignUp: TextView
    private lateinit var authenticationViewModel: AuthenticationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authenticationViewModel =
            ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        initView()
        initEvent()
    }

    private fun initEvent() {
        btnLogin.setOnClickListener(this)
        tvSignUp.setOnClickListener(this)
    }

    private fun initView() {
        btnLogin = findViewById(R.id.btnLogin)
        tvSignUp = findViewById(R.id.tvSignUp)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                login()
            }
            R.id.tvSignUp -> {
                startActivity(Intent(this, RegisterActivity::class.java))
                finish()
            }
        }
    }

    @SuppressLint("ShowToast")
    private fun login() {
        val email: String = edtUserName.text.toString()
        val pass: String = edtPassWord.text.toString()
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu !", Toast.LENGTH_LONG).show()
        } else {
            showDialog(true)
            authenticationViewModel.login(email, pass)
            authenticationViewModel.isSuccess.observe(this, Observer {
                if (it) {
                    showDialog(false)
                    startActivity(Intent(this, ChatActivity::class.java))
                    finish()
                }
            })
            authenticationViewModel.exception.observe(this, Observer {
                showDialog(false)
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            })
        }
    }
}
