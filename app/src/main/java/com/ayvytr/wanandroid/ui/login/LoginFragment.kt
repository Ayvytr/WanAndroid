package com.ayvytr.wanandroid.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.ktx.ui.show
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.base.ResponseObserver
import com.ayvytr.wanandroid.bean.UserInfo
import com.ayvytr.wanandroid.hideInputMethod
import com.ayvytr.wanandroid.local.Kv
import com.ayvytr.wanandroid.setActivityTitle
import com.ayvytr.wanandroid.ui.dialog.LoadingDialog
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * @author Administrator
 */
class LoginFragment : BaseCoroutineFragment<LoginViewModel>() {
    var isLogin = false

    val loadingDialog by lazy {
        LoadingDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        requireActivity().setTitle(R.string.register)
        btn_login.setOnClickListener {
            performLogin()
        }
        btn_register.setOnClickListener {
            performRegister()
        }

        tv_goto_register.setOnClickListener {
            changeLoginMode()
        }

        tv_goto_login.setOnClickListener {
            changeLoginMode()
        }

        changeLoginMode()
    }

    private fun changeLoginMode() {
        isLogin = !isLogin

        ti_repassword.show(!isLogin)
        tv_goto_login.show(!isLogin)
        tv_goto_register.show(isLogin)
        btn_login.show(isLogin)
        btn_register.show(!isLogin)

        setActivityTitle(if (isLogin) R.string.login else R.string.register)
    }

    private fun performRegister() {
        if (cannotRegister()) {
            return
        }

        mViewModel.register(
            et_account.text.toString(),
            et_password.text.toString(),
            et_repassword.text.toString()
        )
    }

    private fun cannotRegister(): Boolean {
        val cannotLogin = cannotLogin()
        if (cannotLogin) {
            return true
        }

        val repassword = et_repassword.text.toString()
        if (repassword.isEmpty()) {
            ti_repassword.error = getString(R.string.please_input_re_password)
            return true
        }

        if (repassword != et_password.text.toString()) {
            showMessage(R.string.inputed_password_not_same)
            return true
        }

        return false
    }

    private fun performLogin() {
        if (cannotLogin()) {
            return
        }
        mViewModel.login(et_account.text.toString(), et_password.text.toString())
    }

    private fun cannotLogin(): Boolean {
        val account = et_account.text.toString()
        if (account.isEmpty()) {
            et_account.error = getString(R.string.please_input_username)
            return true
        }

        val password = et_password.text.toString()
        if (password.isEmpty()) {
            et_password.error = getString(R.string.please_input_password)
            return true
        }

        et_account.error = null
        et_password.error = null
        return false
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        mViewModel.loginLiveData.observe(this, object : ResponseObserver<UserInfo> {
            override fun onSucceed(data: UserInfo) {
                showMessage(R.string.login_succeed)
                Kv.setUserInfo(data)
                findNavController().navigateUp()
            }

            override fun onError(exception: Throwable, message: String) {
                showMessage(message)
            }
        })

        mViewModel.registerLiveData.observe(this, object : ResponseObserver<UserInfo> {
            override fun onSucceed(data: UserInfo) {
                Kv.setUserInfo(data)
                findNavController().navigateUp()
            }

        })
    }

    override fun onStop() {
        super.onStop()
        hideInputMethod()
    }

    override fun showLoading(isShow: Boolean) {
        super.showLoading(isShow)
        if(isShow) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }
}