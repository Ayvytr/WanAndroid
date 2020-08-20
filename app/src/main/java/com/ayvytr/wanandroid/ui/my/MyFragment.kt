package com.ayvytr.wanandroid.ui.my

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ayvytr.coroutine.BaseFragment
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.local.Kv
import com.ayvytr.wanandroid.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * @author Administrator
 */
class MyFragment : BaseFragment<LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_my
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        btn_logout.setOnClickListener {
            mViewModel.logout()
        }
        btn_collect.setOnClickListener {
            findNavController().navigate(if (Kv.isLogin()) R.id.nav_collect else R.id.nav_login)
        }
        iv_header.setOnClickListener {
            if (!Kv.isLogin()) {
                findNavController().navigate(R.id.nav_login)
            }
        }
    }

    override fun initLiveDataObserver() {
        mViewModel.logoutLiveData.observe(this, {
            initUserInfo()
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)

        initUserInfo()
    }

    private fun initUserInfo() {
        val userInfo = Kv.getUserInfo()
        tv_user_name.text =
            if (userInfo.nickname.isEmpty()) userInfo.username else userInfo.nickname
        iv_header.loadImage(userInfo.icon)
        btn_logout.show(Kv.isLogin())
    }
}