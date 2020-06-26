package com.musclegamez.fitness_app.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseFragment
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.settings.model.aboutUs.AboutRequest
import com.musclegamez.fitness_app.util.AlertUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.progressBar
import kotlinx.android.synthetic.main.fragment_aboutus.*

class AboutUsFragment : BaseFragment() {
    private val ARG_OBJECT = "object"
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_aboutus, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        iv_about_arrow.setOnClickListener(clickListener)
        iv_first_arrow.setOnClickListener(clickListener)
        iv_term_arrow.setOnClickListener(clickListener)
        iv_second_arrow.setOnClickListener(clickListener)
        tv_legal_arrow.setOnClickListener(clickListener)
        iv_third_arrow.setOnClickListener(clickListener)


        btn_feedback_submit.setOnClickListener {
            var description = tv_feedback.text.toString()
            if (description.isEmpty())
                tv_feedback.error = resources.getString(R.string.desc_required)
            else
                onAboutUs(description)
        }


    }

    val clickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.iv_about_arrow -> {
                iv_about_arrow.visibility=View.GONE
                iv_first_arrow.visibility=View.VISIBLE
                tv_about_desc.visibility=View.VISIBLE
            }
            R.id.iv_first_arrow -> {
                iv_first_arrow.visibility=View.GONE
                iv_about_arrow.visibility=View.VISIBLE
                tv_about_desc.visibility=View.GONE
            }


            R.id.iv_term_arrow -> {
                iv_term_arrow.visibility=View.GONE
                iv_second_arrow.visibility=View.VISIBLE
                tv_terms_dec.visibility=View.VISIBLE
            }
            R.id.iv_second_arrow -> {
                iv_term_arrow.visibility=View.VISIBLE
                iv_second_arrow.visibility=View.GONE
                tv_terms_dec.visibility=View.GONE
            }

            R.id.tv_legal_arrow -> {
                tv_legal_arrow.visibility=View.GONE
                iv_third_arrow.visibility=View.VISIBLE
                ll_desc.visibility=View.VISIBLE
            }
            R.id.iv_third_arrow -> {
                tv_legal_arrow.visibility=View.VISIBLE
                iv_third_arrow.visibility=View.GONE
                ll_desc.visibility=View.GONE
            }

        }
    }


    fun onAboutUs(desc: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("dsfsdfsdf");
        apiServices.onAbout(AboutRequest(desc))
                .map({ response ->
                    return@map AboutParser.parse(response)

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(about_layout, response)

                    Log.d("Data", response)
                }, { error ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(about_layout, error.message.toString())
                    Log.d("error", error.toString())

                })
    }


    override fun showProgressBar() {
        super.showProgressBar()
        btn_feedback_submit.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_feedback_submit.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

}