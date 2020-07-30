package com.ayvytr.wanandroid.ui.system

import com.ayvytr.adapter.SmartAdapter
import com.ayvytr.adapter.SmartContainer
import com.ayvytr.wanandroid.R
import com.ayvytr.wanandroid.bean.SystemTree
import kotlinx.android.synthetic.main.item_wx_article_category.view.*

/**
 * @author Administrator
 */
class SystemCategoryAdapter(): SmartAdapter<SystemTree>() {
    init {
        map(SmartContainer(R.layout.item_wx_article_category, 0) { it, _ ->
            tv.text = it.name
            tv.isSelected = it.selected
        })
    }

    var currentIndex = -1
        private set

    fun setCurrentIndex(i: Int = 0) {
        if (i < 0 || i >= itemCount) {
            return
        }

        if (currentIndex >= 0) {
            list[currentIndex].selected = false
            notifyItemChanged(currentIndex)
        }

        list.forEach { it.selected = false }
        list[i].selected = true
        notifyItemChanged(i)

        currentIndex = i
    }

//    var currentIndex = -1
//        //        private set
//        set(value) {
//            if (value < 0 || value >= itemCount) {
//                return
//            }
//
//            if (lastIndex >= 0) {
//                list[lastIndex].selected = false
//                notifyItemChanged(lastIndex)
//            }
//
//            list.forEach { it.selected = false }
//            list[value]
//            var oldSelectedIndex = -1
//            list.forEachIndexed { index, it ->
//                if (it.selected) {
//                    oldSelectedIndex = index
//                }
//                it.selected = false
//            }
//
//            list[value].selected = true
//
//            if (oldSelectedIndex != -1) {
//                notifyItemChanged(oldSelectedIndex)
//            }
//            notifyItemChanged(value)
//
//            field = value
//        }

    fun currentCategory(): Int {
        return list[currentIndex].id
    }

    fun currentItem(): SystemTree {
        return list[currentIndex]
    }

}