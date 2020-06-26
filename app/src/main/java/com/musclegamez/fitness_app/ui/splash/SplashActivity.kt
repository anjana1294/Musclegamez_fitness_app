package com.musclegamez.fitness_app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.musclegamez.fitness_app.R
import com.musclegamez.fitness_app.ui.data.pref.SharedPreferencesHelper.init
import com.musclegamez.fitness_app.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000


    internal val mRunnable: Runnable = Runnable {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)
        finish()
//        if (!isFinishing) {
//            var isLoggedIn = SharedPreferencesHelper.getPrefValue(Constants.IS_LOGGED_IN, false)!!;
//            if (isLoggedIn) {
//                var intentHome = Intent(this, HomeActivity::class.java)
//                intentHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                startActivity(intentHome)
//                finish()
//            } else {
//                val intent = Intent(applicationContext, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
//        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        init(this)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}