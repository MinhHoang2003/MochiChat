package vnjp.monstarlablifetime.mochichat.screen.authenication.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_register.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.base.BaseActivity


class RegisterActivity : BaseActivity(), View.OnClickListener {
    private lateinit var edtEmailRegister: EditText
    private lateinit var edtPasswordRegister: EditText
    private lateinit var edtPasswordRetype: EditText
    private lateinit var buttonSignUp: Button
    private lateinit var textHaveAccount: TextView
    private var email: String? = null
    private lateinit var authenticationViewModel: AuthenticationViewModel

    companion object {
        const val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val passPattern = ".{6,20}"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        authenticationViewModel =
            ViewModelProviders.of(this).get(AuthenticationViewModel::class.java)
        initView()
        initEvent()
    }

    private fun initEvent() {
        textHaveAccount.setOnClickListener(this)
        buttonSignUp.setOnClickListener(this)
        edtEmailRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                email = edtEmailRegister.text.toString().trim()
                if (email!!.matches(emailPattern.toRegex()) && s!!.isNotEmpty()) {
                    emailStatus.setVisibility(View.VISIBLE)
                } else {
                    emailStatus.setVisibility(View.GONE)
                    edtEmailRegister.setError("Vui lòng nhập đúng email")

                }
                if (s!!.isEmpty()) {
                    emailStatus.setVisibility(View.GONE)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        edtPasswordRegister.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val password = edtPasswordRegister.text.toString().trim()
                if (password.matches(passPattern.toRegex()) && s!!.isNotEmpty()) {
                    passwordStatus.setVisibility(View.VISIBLE)
                } else {
                    passwordStatus.setVisibility(View.GONE)
                    edtPasswordRegister.setError("Mật khẩu phải lớn hơn 6 kí tự")

                }
                if (s!!.isEmpty()) {
                    passwordStatus.setVisibility(View.GONE)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
        })


        edtPasswordRetype.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passRegis = edtPasswordRegister.text.toString().trim()
                val passRetype = edtPasswordRetype.text.toString().trim()
                if (passRetype.equals(passRegis)) {
                    passwordRetype.setVisibility(View.VISIBLE)
                    passwordRetypeStatus.setVisibility(View.GONE)
                    edtPasswordRetype.setBackgroundResource(R.drawable.bg_edittext)
                } else {
                    edtPasswordRetype.setBackgroundResource(R.drawable.bg_error)
                    passwordRetype.setVisibility(View.GONE)
                    passwordRetypeStatus.setVisibility(View.VISIBLE)

                }
                if (passRetype.isEmpty()) {
                    passwordRetype.setVisibility(View.GONE)
                    passwordRetypeStatus.setVisibility(View.GONE)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


    }


    private fun initView() {
        textHaveAccount = findViewById(R.id.textHaveAccount)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        edtEmailRegister = findViewById(R.id.edtEmailRegister)
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister)
        edtPasswordRetype = findViewById(R.id.edtPasswordRetype)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonSignUp -> {
                val email = edtEmailRegister.text.toString()
                val pass = edtPasswordRegister.text.toString()
                val passRetype = edtPasswordRetype.text.toString()
                if (email.isEmpty() || pass.isEmpty() || passRetype.isEmpty()) {
                    Toast.makeText(
                        this,
                        "Please input your email and password!!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    register(email, pass)
                }
            }
            R.id.textHaveAccount -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
    }

    private fun register(email: String, pass: String) {
        showDialog(true)
        authenticationViewModel.register(email, pass)
        authenticationViewModel.isSuccess.observe(this, Observer {
            showDialog(false)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        })
        authenticationViewModel.exception.observe(this, Observer {
            showDialog(false)
            Toast.makeText(
                this,
                it,
                Toast.LENGTH_LONG
            ).show()
        })
    }
}
