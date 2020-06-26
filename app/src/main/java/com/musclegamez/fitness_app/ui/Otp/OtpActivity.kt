package com.musclegamez.fitness_app.ui.Otp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseActivity
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.Otp.model.OTPRequest
import com.musclegamez.fitness_app.ui.data.pref.SharedPreferencesHelper.setPrefValue
import com.musclegamez.fitness_app.ui.home.HomeActivity
import com.musclegamez.fitness_app.util.AlertUtil
import com.musclegamez.fitness_app.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_otp.*

class OtpActivity : BaseActivity() {
    var email: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        email = intent.getStringExtra("email")
        btn_otp_submit.setOnClickListener(clickListener)


        ed_one.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (ed_one.getText().toString().length === 1)
                    ed_two.requestFocus()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        ed_two.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (ed_two.getText().toString().length === 1) {
                    ed_three.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        ed_three.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (ed_three.getText().toString().length === 1)
                    ed_four.requestFocus()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        ed_four.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (ed_four.getText().toString().length === 1)
                    ed_five.requestFocus()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        ed_five.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (ed_five.getText().toString().length === 1)
                    ed_six.requestFocus()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

    }

    val clickListener = View.OnClickListener { view ->

        when (view.getId()) {

            R.id.btn_otp_submit -> {
                val otp = ed_one.text.toString().trim() +
                        "" + ed_two.text.toString().trim() +
                        "" + ed_three.text.toString().trim() +
                        "" + ed_four.text.toString().trim() +
                        "" + ed_five.text.toString().trim() +
                        "" + ed_six.text.toString().trim()
                if (otp.length < 6)
                    AlertUtil.showSnackBar(otp_layout_main, resources.getString(R.string.invalid_otp))
                else

                   onVerify(email,otp)
            }
        }
    }

    fun onVerify(email: String, otp: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("")
        apiServices.verifyOTP(OTPRequest(`email`, otp))
                .map({ response ->
                    return@map OtpParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ token ->
                    hideProgressBar()
                    setPrefValue(Constants.TOKEN, token)
                    var intentHome = Intent(this, HomeActivity::class.java)
                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intentHome)
                    finish()
                }, { error ->
                    hideProgressBar()
                    Log.d("error", error.toString())
                    AlertUtil.showSnackBar(otp_layout_main, error.message.toString())
                })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun showProgressBar() {
        super.showProgressBar()
        btn_otp_submit.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_otp_submit.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}