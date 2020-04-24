package com.ayvytr.wanandroid.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ayvytr.coroutine.BaseCoroutineActivity
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.ktx.ui.getContext
import com.ayvytr.wanandroid.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.alert

class MainActivity : BaseCoroutineActivity<BaseViewModel>() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home), drawer_layout)
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(
            navController, appBarConfiguration
        )
        nav_view.setupWithNavController(navController)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_home_second)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
