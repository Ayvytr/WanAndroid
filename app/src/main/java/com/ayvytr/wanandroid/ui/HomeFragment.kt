package com.ayvytr.wanandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.ayvytr.coroutine.BaseFragment
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.ui.article.ArticleFragment
import com.ayvytr.wanandroid.ui.ask.AskFragment
import com.ayvytr.wanandroid.ui.my.MyFragment
import com.ayvytr.wanandroid.ui.top.TopFragment
import com.ayvytr.wanandroid.ui.wx.WxArticleFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<BaseViewModel>() {
    private val fragments = listOf(
        ArticleFragment(),
        AskFragment(),
        TopFragment(),
        WxArticleFragment(),
        MyFragment()
    )

    private val titles by lazy {
        listOf(
            getString(R.string.home),
            getString(R.string.ask_per_day),
            "轮播和置顶", "公众号列表",
            "我的"
        )
    }

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
