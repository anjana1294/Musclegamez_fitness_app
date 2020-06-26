package com.musclegamez.fitness_app.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.util.TextviewReg

open class BaseFragment : Fragment(), BaseView {
    private val imageView: ImageView? = null
    private val textviewReg: TextviewReg? = null
    private var progressDialog: ProgressDialog? = null
    override fun context(): Context {
        return context!!
    }

    override fun showEmptyView() {}
    override fun showProgressBar() {}
    override fun hideProgressBar() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
//            Toolbar toolbarHome = getActivity().findViewById(R.id.appbar_layout);
//            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

            //     View toolbarView = getActivity().findViewById(R.id.top_relative);

//            if (this instanceof HomeFragment) {
//                toolbarHome.setTitle("Home");
//            } else if (this instanceof BookingFragment) {
//                toolbar.setTitle("Bookings");
//            } else if (this instanceof OffersFragment) {
//                toolbar.setTitle("Offers");
//            } else if (this instanceof ProfileFragment) {
//                toolbar.setTitle("Profile");
//            }
        }
    }

    override fun showProgressDialog() {
        if (progressDialog != null) {
            if (!progressDialog!!.isShowing) {
                progressDialog!!.show()
            }
        } else {
            progressDialog = ProgressDialog(activity)
            progressDialog!!.setMessage(getString(R.string.processing_msg))
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