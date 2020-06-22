package com.ayvytr.wanandroid.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.loadImage
import com.ayvytr.wanandroid.local.Kv
import com.ayvytr.wanandroid.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * @author Administrator
 */
class MyFragment : BaseCoroutineFragment<LoginViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        btn_logout.setOnClickListener {
            mViewModel.logout()
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.loginLiveData.observe(this, Observer {
            initUserInfo()
        })
        mViewModel.logoutLiveData.observe(this, Observer {
            Kv.clear()
            initUserInfo()
        })

        initUserInfo()
    }

    private fun initUserInfo() {
        val userInfo = Kv.getUserInfo()
        tv_user_name.text =
            if (userInfo.nickname.isEmpty()) userInfo.username else userInfo.nickname
        iv_header.loadImage(userInfo.icon)
        iv_header.setOnClickListener {
            if (!Kv.isLogin()) {
                findNavController().navigate(R.id.nav_login)
            }
        }
        btn_logout.show(Kv.isLogin())
    }
}