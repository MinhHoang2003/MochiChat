package vnjp.monstarlablifetime.mochichat.screen.introduction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import vnjp.monstarlablifetime.mochichat.R
import vnjp.monstarlablifetime.mochichat.screen.authenication.login.LoginActivity
import vnjp.monstarlablifetime.mochichat.screen.authenication.login.RegisterActivity
import vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment.FragmentFirst
import vnjp.monstarlablifetime.mochichat.screen.introduction.introfragment.FragmentSecond


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class IntroductionActivity : AppCompatActivity(), View.OnClickListener,
    ViewPager.OnPageChangeListener {
    private var mSliderDots: LinearLayout? = null
    private var mDotsCount = 0
    private var introductionPaperAdapter: IntroductionPaperAdapter? = null
    private var mDotsView: Array<ImageView?> = arrayOf()
    private var mViewPager: ViewPager? = null
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        initView()
        initEvent()
        setUpSliderDot()
    }


    private fun setUpSliderDot() {
        mDotsCount = introductionPaperAdapter!!.getCount()
        mDotsView = arrayOfNulls<ImageView?>(mDotsCount)

        for (i in 0 until mDotsCount) {
            mDotsView[i] = ImageView(this)
            mDotsView[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.no_active
                )
            )
            val params = LinearLayout.LayoutParams(
                58,
                32
            )
            params.setMargins(8, 0, 8, 0)
            mSliderDots!!.addView(mDotsView[i], params)
        }
        mDotsView[PageType.FIRST]?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.active_page
            )
        )
    }

    private fun initView() {
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)
        introductionPaperAdapter = IntroductionPaperAdapter(supportFragmentManager)
        mSliderDots = findViewById(R.id.slider_dots)
        mViewPager = findViewById(R.id.viewPaper)

    }

    private fun initEvent() {
        btnSignUp.setOnClickListener(this)
        mViewPager?.setAdapter(introductionPaperAdapter)
        mViewPager?.setCurrentItem(PageType.FIRST)
        mViewPager?.addOnPageChangeListener(this)
        btnLogin.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btnSignUp -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        if (position != PageType.FIRST) {
            val fragment: Fragment? =
                supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPaper.toString() + ":" + mViewPager!!.currentItem)
            if (PageType.FIRST === mViewPager!!.getCurrentItem()) {
                val fragmentFirst: FragmentFirst = fragment as FragmentFirst
                val linearLayoutManager: LinearLayoutManager? = fragmentFirst.getLayoutManager()
                linearLayoutManager?.scrollToPosition(0)


            }
        }
        if (position != PageType.SECOND) {
        } else {
            val fragment: Fragment? =
                supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPaper.toString() + ":" + mViewPager!!.currentItem)
            if (fragment != null && PageType.SECOND === mViewPager!!.getCurrentItem()) {
                val fragmentSecond: FragmentSecond = fragment as FragmentSecond
                fragmentSecond.getInstance()

            }
        }
        for (i in 0 until mDotsCount) {
            mDotsView[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.no_active
                )
            )
        }
        mDotsView[position]?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.active_page
            )
        )
    }


}
