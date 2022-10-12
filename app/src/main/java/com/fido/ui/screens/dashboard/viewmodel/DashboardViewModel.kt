package com.fido.ui.screens.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fido.data.repository.Repository
import com.fido.model.server_models.TeslaNewsResponseModel
import com.fido.model.ui_models.BaseDashboardListItemModel
import com.fido.model.ui_models.DashboardListItemLoadingModel
import com.fido.model.ui_models.DashboardListItemModel
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTeslaNews()
    }


    private fun getTeslaNews() = viewModelScope.launch(Dispatchers.IO) {
        when (val response = repository.getDataFromApi()) {
            is NetworkResponse.Success -> {
                parseDashboardListItemsAndUpdateUi(response.body)
            }

            is NetworkResponse.Error -> {
                val message = response.error.message ?: run { return@launch }
                _uiState.update { it.copy(errorMessage = message, state = UiState.State.Error) }
            }
            else -> Unit
        }
    }

    private fun parseDashboardListItemsAndUpdateUi(model: TeslaNewsResponseModel) {
        val dashboardListItems = mutableListOf<DashboardListItemModel>()
        model.articles.forEach { article ->
            dashboardListItems.add(DashboardListItemModel(article.urlToImage ?: "", article.title))
        }
        _uiState.update {
            it.copy(dashboardListItems = dashboardListItems, state = UiState.State.Data) }
    }

    data class UiState(
        val dashboardListItems: List<BaseDashboardListItemModel> = listOf(DashboardListItemLoadingModel()),
        val errorMessage: String = "",
        val state: State = State.Initial
    ) {
        enum class State {
            Initial,
            Data,
            Error
        }
    }

    sealed interface UiEvents {
        object ListItemClicked : UiEvents
        object UiResumedFromBackground : UiEvents
        object UserReturnedToScreen : UiEvents
    }
}