package com.a.activities

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.a.R
import com.example.baseproject.api.ApiException
import java.io.IOException

abstract class BaseActivity : AppCompatActivity(){

    protected abstract fun initLayout(): Int
    protected abstract fun initComponents()
    protected abstract fun addListener()

    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())

        mProgressDialog = Dialog(this, R.style.MyProgressDialog)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        mProgressDialog.setCancelable(false)

        initComponents()
        addListener()
    }

    fun showLoading(isShow: Boolean) {
        if (isShow)
            mProgressDialog.show()
        else mProgressDialog.dismiss()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.trans_right_to_left_in, R.anim.trans_right_to_left_out)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        overridePendingTransition(R.anim.trans_right_to_left_in, R.anim.trans_right_to_left_out)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        overridePendingTransition(R.anim.trans_left_to_right_in, R.anim.trans_left_to_right_out)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.trans_left_to_right_in, R.anim.trans_left_to_right_out)
    }

    fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showAlert(e: Exception?) {
        when (e) {
            is ApiException -> showAlert("" + e.errorMessage)
            is IOException -> showAlert(getString(R.string.err_network_available))
            null -> showAlert(getString(R.string.err_unexpected_exception))
            else -> showAlert("" + e.message)
        }
    }

    fun showAlert(message: String?) {
        AlertDialog
            .Builder(this)
            .setMessage(message)
            .setPositiveButton(R.string.txt_ok, null)
            .show()
    }

    fun showAlert(message: String?, posString: String, posListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(posString, posListener)
            .show()
    }

    fun showAlert(message: String?, positiveButton: Int, negativeButton: Int, positiveListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@BaseActivity).apply {
            setMessage(message)
            setPositiveButton(positiveButton, positiveListener)
            setNegativeButton(negativeButton, null)
            create()
            show()
        }
    }

    fun showAlert(message: String, positiveButton: Int, positiveListener: DialogInterface.OnClickListener, negativeButton: Int, negativeListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this).apply {
            setMessage(message)
            setCancelable(false)
            setPositiveButton(positiveButton, positiveListener)
            setNegativeButton(negativeButton, negativeListener)
            create()
            show()
        }
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
