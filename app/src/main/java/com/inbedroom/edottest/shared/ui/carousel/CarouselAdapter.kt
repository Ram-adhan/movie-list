package com.inbedroom.edottest.shared.ui.carousel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inbedroom.edottest.databinding.ItemCarouselBinding
import com.inbedroom.edottest.feature.list.Movie

class CarouselAdapter(
    private var dataList: List<Movie>,
    private val maxItemHeight: Int? = null,
    private val maxItemWidth: Int? = null,
) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.count()

    inner class ViewHolder(private val binding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            maxItemHeight?.let {
                itemView.layoutParams.height = it
            }
            maxItemWidth?.let {
                itemView.layoutParams.width = it
            }
        }

        fun bind(item: Movie) {
            if (item.image.isNotBlank()) {
                Glide.with(itemView.context)
                    .load(item.image)
                    .into(binding.imageView)
            }
        }
    }
}