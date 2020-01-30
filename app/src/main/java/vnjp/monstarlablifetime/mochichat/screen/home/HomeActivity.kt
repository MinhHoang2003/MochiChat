package vnjp.monstarlablifetime.mochichat.screen.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.landing_page_screen.*
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.data.repository.ChatsRepository
import vnjp.monstarlablifetime.mochichat.screen.SettingFragment
import vnjp.monstarlablifetime.mochichat.screen.contacts.ContactsFragment
import vnjp.monstarlablifetime.mochichat.screen.groups.FragmentGroups
import vnjp.monstarlablifetime.mochichat.screen.inTransaction

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

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

//        ChatsRepository().getChats("chat01",
//            onDataLoad = { item ->
//                Log.d("firebase", item.image.toString())
//            }, onFailure = {
//
//            })
    }

    private fun init() {
        bottomNavigation.itemIconTintList = null
    }

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
                fragment = SettingFragment()
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
