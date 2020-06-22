package com.ayvytr.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ayvytr.coroutine.BaseCoroutineFragment
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.ui.article.ArticleFragment
import com.ayvytr.wanandroid.ui.ask.AskFragment
import com.ayvytr.wanandroid.ui.my.MyFragment
import com.ayvytr.wanandroid.ui.project.ProjectFragment
import com.ayvytr.wanandroid.ui.square.SquareFragment
import com.ayvytr.wanandroid.ui.top.TopFragment
import com.ayvytr.wanandroid.ui.wx.WxArticleFragment
import com.ayvytr.wanandroid.uisearch.SearchFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseCoroutineFragment<BaseViewModel>() {
    private val fragments = listOf(
        SearchFragment(),
        TopFragment(),
        AskFragment(),
        ArticleFragment(),
        ProjectFragment(),
        SquareFragment(),
        WxArticleFragment(),
        MyFragment()
    )
    private val titles = listOf(
        "搜一搜", "轮播和置顶", "问答", "首页文章", "最新项目", "广场", "公众号列表", "我的"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        val adapter = object : FragmentPagerAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return titles[position]
            }
        }
        vp.adapter = adapter
//        vp.offscreenPageLimit = adapter.count
        tab_layout.setupWithViewPager(vp)
    }
}
