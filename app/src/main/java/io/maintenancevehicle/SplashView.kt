package io.maintenancevehicle

import android.content.Intent
import android.os.Handler
import android.os.Looper
import io.maintenancevehicle.bases.BaseActivity
import io.maintenancevehicle.databinding.ActivitySplashViewBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.SharedPreferences
import io.maintenancevehicle.views.login.LoginActivity

class SplashView : BaseActivity<ActivitySplashViewBinding>(
    ActivitySplashViewBinding::inflate
) {

    override fun initView() {
        super.initView()
        Handler(Looper.getMainLooper()).postDelayed({
            finishAndRemoveTask()
            val isLogin = SharedPreferences.getValue(
                Constants.IS_LOGIN,
                false
            )
            if (isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }, 2000)
    }
}