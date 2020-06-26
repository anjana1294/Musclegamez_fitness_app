package com.musclegamez.fitness_app.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


abstract class BaseActivity : AppCompatActivity(), BaseView {

    private var progressDialog: ProgressDialog? = null
    //    var locationUpdatesObservable  : Loca

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun context(): Context {
        return this
    }

    override fun showEmptyView() {}

    override fun showProgressBar() {}

    override fun hideProgressBar() {}

    override fun showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog!!.isShowing) {
                progressDialog!!.show()
                progressDialog!!.setCancelable(false)
            }
        } else {
            progressDialog = ProgressDialog(this)
            //            progressDialog.setMessage(getString(R.string.processing_msg));
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