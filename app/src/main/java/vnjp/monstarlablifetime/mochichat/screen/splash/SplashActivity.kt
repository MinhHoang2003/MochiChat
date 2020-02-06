package vnjp.monstarlablifetime.mochichat.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.screen.home.HomeActivity
import vnjp.monstarlablifetime.mochichat.screen.introduction.IntroductionActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)
        initView()
        Handler().postDelayed({
            var intent: Intent? = null
            val user = FirebaseAuth.getInstance().currentUser
            intent = if (user != null) {
                Intent(this, HomeActivity::class.java)
            } else {
                Intent(this, IntroductionActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun initView() {

    }
}


