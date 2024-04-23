package io.maintenancevehicle.views.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.MainActivity
import io.maintenancevehicle.bases.BaseActivity
import io.maintenancevehicle.databinding.ActivityLoginBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.SharedPreferences

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(
    ActivityLoginBinding::inflate
) {

    private val loginViewModel by viewModels<LoginViewModel>()

    override fun observerData() {
        super.observerData()
        loginViewModel.loginFlag.observe(this) { loginFlag ->
            if (loginFlag) {
                if (binding.savePassword.isChecked) {
                    SharedPreferences.setValue(
                        Constants.IS_LOGIN,
                        true
                    )
                    SharedPreferences.setValue(
                        Constants.USER_ID,
                        binding.userName.text.toString() + binding.password.text.toString()
                    )
                }
                finishAndRemoveTask()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(
                    this,
                    "Đăng nhập thất bại!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnLogin.setOnClickListener {
            loginViewModel.login(
                userName = binding.userName.text.toString(),
                password = binding.password.text.toString()
            )
        }
    }

}