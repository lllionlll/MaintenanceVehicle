package io.maintenancevehicle

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseActivity
import io.maintenancevehicle.data.model.User
import io.maintenancevehicle.databinding.ActivitySplashViewBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.SharedPreferences
import io.maintenancevehicle.views.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashView : BaseActivity<ActivitySplashViewBinding>(
    ActivitySplashViewBinding::inflate
) {

    private val splashViewModel by viewModels<SplashViewModel>()

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
                runBlocking {
                    splashViewModel.deleteUser()
                    splashViewModel.saveUser(
                        listOf(
                            User(
                                userName = "admin",
                                password = "123456"
                            ),
                            User(
                                userName = "admin1",
                                password = "123456"
                            )
                        )
                    )
                    startActivity(Intent(this@SplashView, LoginActivity::class.java))
                }
            }
        }, 2000)
    }
}