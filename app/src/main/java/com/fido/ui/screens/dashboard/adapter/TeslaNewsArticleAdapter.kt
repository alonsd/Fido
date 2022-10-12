package com.fido.ui.screens.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fido.databinding.ViewholderTeslaNewsArticleBinding
import com.fido.databinding.ViewholderTeslaNewsArticleLoadingBinding
import com.fido.model.ui_models.BaseDashboardListItemModel
import com.fido.model.ui_models.DashboardListItemModel
import com.fido.utils.adapter.DefaultAdapterDiffUtilCallback

class TeslaNewsArticleAdapter(
    private val onclick: (model: DashboardListItemModel) -> Unit
) : ListAdapter<BaseDashboardListItemModel,
        RecyclerView.ViewHolder>(DefaultAdapterDiffUtilCallback<BaseDashboardListItemModel>()) {

    private val teslaNewsItemType = 0
    private val teslaNewsLoadingType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == teslaNewsItemType) {
            val binding = ViewholderTeslaNewsArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return TeslaNewsArticleViewHolder(binding, onclick)
        }
        val binding = ViewholderTeslaNewsArticleLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeslaNewsLoadingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (holder is TeslaNewsArticleViewHolder && currentItem is DashboardListItemModel) {
            return holder.bind(currentItem)
        }
    }


    override fun getItemViewType(position: Int): Int {
        val currentItem = getItem(position)
        return if (currentItem is DashboardListItemModel) {
            teslaNewsItemType
        } else {
            teslaNewsLoadingType
        }
    }

}