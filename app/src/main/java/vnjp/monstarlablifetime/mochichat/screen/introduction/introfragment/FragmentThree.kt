package vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vnjp.monstarlablifetime.mochichat.R

class FragmentThree : Fragment() {
    fun getInstance(): FragmentThree {
        return FragmentThree()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_three, container, false)

        return view
    }
}