package com.fido.ui.screens.dashboard.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fido.R
import com.fido.databinding.ViewholderTeslaNewsArticleBinding
import com.fido.model.ui_models.DashboardListItemModel

class TeslaNewsArticleViewHolder(private val binding : ViewholderTeslaNewsArticleBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model : DashboardListItemModel){
        if (model.articleImage == null) {
            binding.viewholderTeslaNewsArticleImage.setImageResource(R.mipmap.ic_launcher)
        } else {
            Glide.with(binding.root.context)
                .load(model.articleImage)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.viewholderTeslaNewsArticleImage)
        }
        binding.viewholderTeslaNewsArticleTitle.text = model.articleTitle
    }
}