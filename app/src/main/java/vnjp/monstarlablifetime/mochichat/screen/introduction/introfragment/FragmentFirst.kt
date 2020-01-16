package vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import vnjp.monstarlablifetime.mochichat.R

class FragmentFirst : Fragment() {
    private var mLayoutManager: LinearLayoutManager? = null
    fun getInstance(): FragmentFirst {
        return FragmentFirst()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmen_first, container, false)
        mLayoutManager = LinearLayoutManager(activity)
        return view
    }

    fun getLayoutManager(): LinearLayoutManager? {
        return mLayoutManager
    }
}