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
import com.ayvytr.wanandroid.ui.main.ui.home.HomeSecondFragment
import com.ayvytr.wanandroid.ui.article.ArticleFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseCoroutineFragment<BaseViewModel>() {
    private val fragments = listOf(ArticleFragment(), HomeSecondFragment())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        vp.adapter = object:FragmentPagerAdapter(
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
                return position.toString()
            }
        }
        tab_layout.setupWithViewPager(vp)
    }
}
