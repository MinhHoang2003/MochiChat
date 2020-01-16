package vnjp.monstarlablifetime.mochichat.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.screen.introduction.IntroductionActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)
        initView()
        Handler().postDelayed({
            val intent = Intent(this, IntroductionActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    private fun initView() {

    }
}


