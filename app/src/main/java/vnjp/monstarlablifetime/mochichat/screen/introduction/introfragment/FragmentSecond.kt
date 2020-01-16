package vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vnjp.monstarlablifetime.mochichat.R

class FragmentSecond : Fragment() {
    fun getInstance(): FragmentSecond {
        return FragmentSecond()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second,container,false)

        return view
    }
}