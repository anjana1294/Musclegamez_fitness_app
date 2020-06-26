package com.musclegamez.fitness_app.ui.signup

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.base.BaseActivity
import com.musclegamez.fitness_app.custom.SpannyText
import com.musclegamez.fitness_app.network.NetworkClient
import com.musclegamez.fitness_app.ui.Otp.OtpActivity
import com.musclegamez.fitness_app.ui.signup.model.SignupRequest
import com.musclegamez.fitness_app.util.AlertUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*


class SignupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val spannyText = SpannyText(
                resources.getString(R.string.already_have_an_account), ForegroundColorSpan(
                ContextCompat.getColor(this, R.color.colorWhite)
        )).append(
                " LOGIN", ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)),
                StyleSpan(Typeface.BOLD)
        )

        tv_already_have_an_account!!.text = spannyText
        tv_already_have_an_account.setOnClickListener(clickListener)
        btn_signup.setOnClickListener(clickListener)


    }

    val clickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.tv_already_have_an_account -> {
                finish()
            }
            R.id.btn_signup -> {
                var email = tv_signup_email.text.toString()
                var phoneNo = tv_signup_phone.text.toString()
                var password = tv_signup_password.text.toString()

                if (email.isEmpty())
                    tv_signup_email.error = resources.getString(R.string.email_required)
                else if (phoneNo.isEmpty())
                    tv_signup_phone.error = resources.getString(R.string.password_required)
                else if (password.isEmpty())
                    tv_signup_password.error = resources.getString(R.string.password_required)
                else
                  onSignUp(email,password,phoneNo)
            }

        }
    }
    fun onSignUp( email: String, password: String,phone: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("");
        apiServices.signup(SignupRequest(email, password, phone))
                .map({ response ->
                    return@map SignUpParser.parse(response)

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
            hideProgressBar()
            var intent = Intent(this, OtpActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            AlertUtil.showSnackBar(layout_main, response)
            Log.d("Data", response)
        }, { error ->
            hideProgressBar()
            AlertUtil.showSnackBar(layout_main, error.message.toString())
            Log.d("error", error.toString())

        })
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun showProgressBar() {
        super.showProgressBar()
        btn_signup.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_signup.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}