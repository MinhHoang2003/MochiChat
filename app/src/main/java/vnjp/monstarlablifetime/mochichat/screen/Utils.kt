package vnjp.monstarlablifetime.mochichat.screen

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object Utils {
    const val user: String = "test01"
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}