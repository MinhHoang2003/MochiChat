package vnjp.monstarlablifetime.mochichat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.landing_page_screen.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_screen)
        bottomNavigation.itemIconTintList = null
        expandedLayoutChatHistory.duration = 100
        buttonShowChatHistory.setOnClickListener {
            if (expandedLayoutChatHistory.isExpanded) expandedLayoutChatHistory.collapse()
            else expandedLayoutChatHistory.expand()
        }
    }
}
