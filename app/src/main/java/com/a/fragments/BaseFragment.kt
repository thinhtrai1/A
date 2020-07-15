package com.a.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.a.R
import com.example.baseproject.api.ApiException
import java.io.IOException

abstract class BaseFragment : Fragment() {
    lateinit var mView: View
    lateinit var mContext: Context
    private lateinit var mProgressDialog: Dialog

    protected abstract fun initLayout(): Int
    protected abstract fun initComponents()
    protected abstract fun addListener()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = LayoutInflater.from(activity).inflate(initLayout(), container, false)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProgressDialog = Dialog(mContext, R.style.MyProgressDialog)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        mProgressDialog.setCancelable(false)

        initComponents()
        addListener()
    }

    fun setFragment(fragment: Fragment, containerId: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

    fun addFragment(fragment: Fragment, containerId: Int) {
        childFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.trans_right_to_left_in, R.anim.trans_right_to_left_out,
                R.anim.trans_left_to_right_in, R.anim.trans_left_to_right_out)
            .add(containerId, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    fun showLoading(isShow: Boolean) {
        if (isShow)
            mProgressDialog.show()
        else mProgressDialog.dismiss()
    }

    fun showToast(message: String?) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
    }

    fun showAlert(message: String?) {
        AlertDialog
            .Builder(mContext)
            .setMessage(message)
            .setPositiveButton(R.string.txt_ok, null)
            .show()
    }

    fun showAlert(e: Exception?) {
        when (e) {
            is ApiException -> showAlert("" + e.errorMessage)
            is IOException -> showAlert(getString(R.string.err_network_available))
            null -> showAlert(getString(R.string.err_unexpected_exception))
            else -> showAlert("" + e.message)
        }
    }

    fun hideKeyboard(context: Context, view: View) {
        (mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
    }
}
