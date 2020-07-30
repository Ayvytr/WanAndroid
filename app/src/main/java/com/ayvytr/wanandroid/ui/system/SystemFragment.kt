package com.ayvytr.wanandroid.ui.system

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.ayvytr.coroutine.BaseFragment
import com.ayvytr.coroutine.observer.WrapperObserver
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.SystemTree
import kotlinx.android.synthetic.main.fragment_system.*

/**
 * @author Administrator
 */
class SystemFragment: BaseFragment<SystemViewModel>() {
    val systemCategoryAdapter by lazy {
        val adapter = SystemCategoryAdapter()
        adapter.itemClickListener = { it, i ->
            changeCategory(it)
            adapter.setCurrentIndex(i)
        }
        adapter
    }

    val systemSubCategoryAdapter by lazy {
        val adapter = SystemCategoryAdapter()
        adapter.itemClickListener = { it, i ->
            val action = SystemFragmentDirections.navGotoSystemList(it, systemCategoryAdapter.currentItem().name)
            findNavController().navigate(action)
        }
        adapter
    }

    private fun changeCategory(it: SystemTree) {
        systemSubCategoryAdapter.update(it.children)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_system
    }

    override fun initLiveDataObserver() {
        super.initLiveDataObserver()
        mViewModel.systemCategoryLiveData.observe(this, object:
            WrapperObserver<List<SystemTree>>() {
            override fun onSucceed(data: List<SystemTree>) {
                systemCategoryAdapter.update(data)
                systemCategoryAdapter.setCurrentIndex()
                changeCategory(data[0])
                status_view.showContent()
            }
        })
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        rv_category.adapter = systemCategoryAdapter
        rv.adapter = systemSubCategoryAdapter
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        status_view.showLoading()
        mViewModel.getSystemCategory()
    }
}