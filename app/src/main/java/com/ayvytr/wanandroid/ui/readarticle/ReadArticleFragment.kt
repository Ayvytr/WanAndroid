package com.ayvytr.wanandroid.ui.readarticle

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.ktx.ui.setActivityTitle
import com.ayvytr.network.bean.ResponseWrapper
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.ui.base.BaseArticleAdapter
import com.ayvytr.wanandroid.ui.base.BaseArticleFragment

/**
 * @author Administrator
 */
class ReadArticleFragment : BaseArticleFragment() {
    override fun getListLiveData(): MutableLiveData<ResponseWrapper<List<Article>>> {
        return mViewModel.readArticleLiveData
    }

    override fun loadData(page: Int, isLoadMore: Boolean) {
        mViewModel.getReadArticleList(page, isLoadMore)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        setActivityTitle(R.string.had_read_local_save)
    }

    override fun getAdapter(): SmartAdapter<Article> {
        val adapter = BaseArticleAdapter(requireContext()) { it, position ->
            performCollect(it.id, it.collect, position)
        }
        adapter.isShowRead = false
        adapter.isShowCollect = false
        return adapter
    }
}