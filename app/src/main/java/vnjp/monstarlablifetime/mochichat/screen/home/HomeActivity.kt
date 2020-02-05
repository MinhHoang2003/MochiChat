package vnjp.monstarlablifetime.mochichat.screen.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.landing_page_screen.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.screen.contacts.ContactsFragment
import vnjp.monstarlablifetime.mochichat.screen.groups.FragmentGroups
import vnjp.monstarlablifetime.mochichat.screen.inTransaction
import vnjp.monstarlablifetime.mochichat.screen.setting.SettingFragment

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var toolbar: Toolbar
    private lateinit var toolbarTitle: TextView

    companion object {
        const val CHATS = "Chats"
        const val GROUPS = "Your group chats"
        const val CONTACT = "Contacts"
        const val SETTING = "Setting"
    }

    private var currentFragment: String = CHATS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing_page_screen)
        init()
        loadFragment(ChatsFragment())
    }

    private fun init() {
        setSupportActionBar(toolBar)
        toolbar = findViewById(R.id.toolBar)
        toolbarTitle = toolbar.findViewById(R.id.titleLandingPage)
        toolbar.setNavigationIcon(R.drawable.ic_back)
        setSupportActionBar(toolbar)
        bottomNavigation.itemIconTintList = null
        bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var fragment: Fragment = ChatsFragment()
        when (p0.itemId) {
            R.id.menu_chats -> {
                fragment = ChatsFragment()
                currentFragment = CHATS
            }
            R.id.menu_groups -> {
                fragment = FragmentGroups()
                currentFragment = GROUPS
            }
            R.id.menu_friends -> {
                fragment = ContactsFragment()
                currentFragment = CONTACT
            }
            R.id.menu_setting -> {
                fragment =
                    SettingFragment()
                currentFragment = SETTING
            }

        }
        loadFragment(fragment)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction {
            replace(R.id.fragmentContainer, fragment, currentFragment)
        }
    }
}
