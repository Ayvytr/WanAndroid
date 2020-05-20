package com.ayvytr.wanandroid.ui.top

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ayvytr.wanandroid.bean.Banner
import com.ayvytr.wanandroid.ui.top.ImageAdapter.BannerViewHolder
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter

class ImageAdapter(mDatas: List<Banner>?) :
    BannerAdapter<Banner?, BannerViewHolder>(mDatas) {
    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView = ImageView(parent.context)
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }


    inner class BannerViewHolder(var imageView: ImageView) :
        RecyclerView.ViewHolder(imageView) {

        fun bind(data: Banner, position: Int) {
            Glide.with(imageView.context)
                .load(data.imagePath)
                .into(imageView)
        }

    }

    override fun onBindView(holder: BannerViewHolder?, data: Banner?, position: Int, size: Int) {
        holder?.bind(data!!, position)
    }
}