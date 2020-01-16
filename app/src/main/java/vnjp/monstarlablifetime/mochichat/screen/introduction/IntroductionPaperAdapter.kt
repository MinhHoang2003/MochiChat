package vnjp.monstarlablifetime.mochichat.screen.introduction

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment.FragmentFirst
import vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment.FragmentSecond
import vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment.FragmentThree

@Suppress("DEPRECATION")
class IntroductionPaperAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        when (position) {
            PageType.FIRST -> return FragmentFirst()
            PageType.SECOND -> return FragmentSecond()
            PageType.THREE -> return FragmentThree()
        }
        return Fragment()
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    companion object {
        const val TAB_COUNT = 3
    }
}