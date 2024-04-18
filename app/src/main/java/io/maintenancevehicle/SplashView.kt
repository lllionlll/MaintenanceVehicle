package io.maintenancevehicle

import android.content.Intent
import android.os.Handler
import android.os.Looper
import io.maintenancevehicle.bases.BaseActivity
import io.maintenancevehicle.databinding.ActivitySplashViewBinding

class SplashView : BaseActivity<ActivitySplashViewBinding>(
    ActivitySplashViewBinding::inflate
) {

    override fun initView() {
        super.initView()
        Handler(Looper.getMainLooper()).postDelayed({
            finishAndRemoveTask()
            startActivity(Intent(this, MainActivity::class.java))

        }, 2000)
    }
}