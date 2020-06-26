package com.musclegamez.fitness_app.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.musclegamez.fitness_app.ui.data.pref.SharedPreferencesHelper.getPrefValue
import com.musclegamez.fitness_app.ui.data.pref.SharedPreferencesHelper.setPrefValue
import com.musclegamez.fitness_app.ui.home.HomeActivity
import com.musclegamez.fitness_app.ui.login.model.LoginRequest
import com.musclegamez.fitness_app.ui.signup.SignupActivity
import com.musclegamez.fitness_app.util.AlertUtil
import com.musclegamez.fitness_app.util.Constants.Companion.IS_LOGGED_IN
import com.musclegamez.fitness_app.util.Constants.Companion.TOKEN
import com.musclegamez.fitness_app.util.RxUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    private var disposable: Disposable? = null
    private lateinit var context: Context
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getPrefValue(TOKEN, "")!!

        val spannyText = SpannyText(
                resources.getString(R.string.donot_have_any_account), ForegroundColorSpan(
                ContextCompat.getColor(this, R.color.colorWhite)
        )).append(
                " SIGN UP", ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorRed)),
                StyleSpan(Typeface.BOLD)
        )
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)

        tv_dnt_have_account!!.text = spannyText
        tv_dnt_have_account.setOnClickListener(clickListener)
        btn_login.setOnClickListener(clickListener)

    }

    val clickListener = View.OnClickListener { view ->
        when (view.getId()) {
            R.id.tv_dnt_have_account -> {
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.btn_login -> {
                var email = tv_username.text.toString()
                var password = tv_password.text.toString()
                if (email.isEmpty())
                    tv_username.error = resources.getString(R.string.email_required)
                else if (password.isEmpty())
                    tv_password.error = resources.getString(R.string.password_required)
                else {
//                    val intentHome = Intent(this, HomeActivity::class.java)
//                     startActivity(intentHome)
//                      finish();
                    OnLogin(email, password)

                }


            }

        }
    }


    fun OnLogin(email: String, password: String) {
        showProgressBar()
        var apiServices = NetworkClient.create("");
        disposable = apiServices.login(LoginRequest(email, password))
                .map({ response ->
                    return@map LoginParser.parse(response)
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ msg ->
                    hideProgressBar()
                    setPrefValue(TOKEN, msg)
                    setPrefValue(IS_LOGGED_IN, true)

                    var intentHome = Intent(this, HomeActivity::class.java)
                    intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intentHome)
                    finish()

                    Log.d("Data", getPrefValue(TOKEN))
                }, { error ->
                    hideProgressBar()
                    AlertUtil.showSnackBar(layout_main, error.message.toString())
                    Log.d("error", error.message)

                })
    }


    override fun showProgressBar() {
        super.showProgressBar()
        btn_login.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        super.hideProgressBar()
        btn_login.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        RxUtils.dispose(disposable)
        super.onDestroy()
    }
}