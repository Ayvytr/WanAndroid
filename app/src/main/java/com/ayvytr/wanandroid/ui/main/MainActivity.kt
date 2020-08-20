package com.ayvytr.wanandroid.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ayvytr.coroutine.BaseActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity<BaseViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val navIds = setOf(
        R.id.nav_home,
        R.id.nav_had_read,
        R.id.nav_search,
        R.id.nav_system,
        R.id.nav_square,
        R.id.nav_newest_project
    )

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(navIds, drawer_layout)
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        moveTaskToBack(false)
//        super.onBackPressed()
//    }
}
