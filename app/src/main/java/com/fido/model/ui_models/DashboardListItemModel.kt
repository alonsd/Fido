package com.fido.model.ui_models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardListItemModel(
    val articleImage : String?,
    val articleTitle : String,
    val description : String
) :BaseDashboardListItemModel(), Parcelable


