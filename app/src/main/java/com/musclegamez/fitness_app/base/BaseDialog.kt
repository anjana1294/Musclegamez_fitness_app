package com.musclegamez.fitness_app.base

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment

class BaseDialog : DialogFragment(), BaseView {
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        dialog?.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun context(): Context {
        return context!!
    }

    override fun showEmptyView() {}
    override fun showProgressBar() {}
    override fun hideProgressBar() {}
    override fun showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog!!.isShowing) {
                progressDialog!!.show()
            }
        } else {
            progressDialog = ProgressDialog(activity)
            //     progressDialog.setMessage(getString(R.string.processing_msg));
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }
    }

    override fun hideProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }

    override fun showMessage(msg: String) {}
}