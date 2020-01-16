package vnjp.monstarlablifetime.mochichat.screen.introduction

import androidx.annotation.IntDef

import vnjp.monstarlablifetime.mochichat.screen.introduction.PageType.Companion.FIRST
import vnjp.monstarlablifetime.mochichat.screen.introduction.PageType.Companion.SECOND
import vnjp.monstarlablifetime.mochichat.screen.introduction.PageType.Companion.THREE

@IntDef(*[FIRST, SECOND, THREE])
internal annotation class PageType {
    companion object {
        const val FIRST = 0
        const val SECOND = 1
        const val THREE = 2

    }
}
