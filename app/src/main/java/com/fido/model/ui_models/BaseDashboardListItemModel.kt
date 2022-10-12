package com.fido.model.ui_models

import com.fido.utils.adapter.DefaultAdapterDiffUtilCallback
import java.util.UUID

abstract class BaseDashboardListItemModel : DefaultAdapterDiffUtilCallback.ModelWithId {
    override fun fetchId(): String = UUID.randomUUID().toString()
}