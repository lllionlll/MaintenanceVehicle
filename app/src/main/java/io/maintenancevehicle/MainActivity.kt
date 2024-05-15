package io.maintenancevehicle

import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseActivity
import io.maintenancevehicle.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {



}