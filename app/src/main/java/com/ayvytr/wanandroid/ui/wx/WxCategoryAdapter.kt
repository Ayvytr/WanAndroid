package com.ayvytr.wanandroid.ui.wx

import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.adapter.SmartContainer
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.WxArticleCategory
import kotlinx.android.synthetic.main.item_wx_article_category.view.*

/**
 * @author Administrator
 */
class WxCategoryAdapter() :
    SmartAdapter<WxArticleCategory>() {
    init {
        map(SmartContainer(R.layout.item_wx_article_category, 0) { it, _ ->
            tv.text = it.name
            tv.isSelected = it.selected
        })
    }


    var currentIndex = -1
        //        private set
        set(value) {
            if(value < 0) {
                return
            }

            var oldSelectedIndex = -1
            list.forEachIndexed { index, it ->
                if (it.selected) {
                    oldSelectedIndex = index
                }
                it.selected = false
            }

            list[value].selected = true

            if (oldSelectedIndex != -1) {
                notifyItemChanged(oldSelectedIndex)
            }
            notifyItemChanged(value)

            field = value
        }

    fun currentCategory(): Int {
        return list[currentIndex].id
    }

    fun resetIndex() {
        if (isEmpty()) {
            currentIndex = -1
        } else {
            currentIndex = 0
            list[currentIndex].selected = true
        }

    }

}