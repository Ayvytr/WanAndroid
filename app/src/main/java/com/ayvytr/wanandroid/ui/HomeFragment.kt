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
import com.ayvytr.wanandroid.ui.project.ProjectFragment
import com.ayvytr.wanandroid.ui.wx.WxArticleFragment
import com.ayvytr.wanandroid.ui.square.SquareFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseCoroutineFragment<BaseViewModel>() {
    private val fragments = listOf(
        ArticleFragment(),
        ProjectFragment(),
        SquareFragment(),
        WxArticleFragment()
    )
    private val titles = listOf("首页文章", "最新项目", "广场", "公众号列表")

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
