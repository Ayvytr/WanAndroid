package com.ayvytr.wanandroid.ui.project

import androidx.lifecycle.MutableLiveData
import com.ayvytr.coroutine.viewmodel.BaseViewModel
import com.ayvytr.wanandroid.bean.Article
import com.ayvytr.wanandroid.bean.PageBean
import com.ayvytr.wanandroid.ui.Repository

class ProjectViewModel : BaseViewModel() {
    val repository = Repository()
    val projectLiveData = MutableLiveData<PageBean<Article>>()

    fun getProject(page: Int, isLoadMore: Boolean = false) {
        launchLoading {
            val mainProject = repository.getMainProject(page)
            mainProject.throwFailedException()
            projectLiveData.postValue(
                PageBean(
                    page,
                    isLoadMore,
                    mainProject.data.datas,
                    mainProject.data.hasMore()
                )
            )
        }
    }

}