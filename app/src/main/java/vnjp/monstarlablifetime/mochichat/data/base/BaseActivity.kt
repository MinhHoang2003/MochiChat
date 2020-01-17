package vnjp.monstarlablifetime.mochichat.data.base

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import vnjp.monstarlablifetime.mochichat.R

@SuppressLint("Registered")
@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {
    protected var loadingMsg = "Đang xử lý"
    private var mDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseInit()
    }

    fun setLoadingMessage(msg: String?) {
        mDialog!!.setMessage(msg)
    }

    fun baseInit() {
        mDialog = ProgressDialog(this, R.style.AppCompatAlertDialogStyle)
        mDialog!!.setMessage(loadingMsg)
    }

    fun showDialog(isShow: Boolean) {
        if (isShow) {
            if (mDialog != null && !mDialog!!.isShowing) {
                mDialog!!.show()
            }
        } else {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
        }
    }
}
