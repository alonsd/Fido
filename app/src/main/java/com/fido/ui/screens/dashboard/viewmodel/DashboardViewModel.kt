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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    private val uiEvents = _uiEvent.asSharedFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiActions = _uiAction.asSharedFlow()

    init {
        getTeslaNews()
        observerUiEvents()
    }

    private fun observerUiEvents() = viewModelScope.launch {
        uiEvents.collect { event ->
            when (event) {
                is UiEvent.ListItemClicked -> {
                    updateUiAction(UiAction.NavigateToArticleDetails(event.model))
                }
                UiEvent.UserReturnedToScreen -> {
                    addLoadingItemToTopOfTheList()
                    getTeslaNews()
                }
            }
        }
    }

    private fun addLoadingItemToTopOfTheList() {
        _uiState.update {
            val items = it.dashboardListItems.toMutableList()
            items.add(0, DashboardListItemLoadingModel())
            it.copy(dashboardListItems = items)
        }
    }

    private fun updateUiAction(action: UiAction) = viewModelScope.launch {
        _uiAction.emit(action)
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
            dashboardListItems.add(
                DashboardListItemModel(
                    article.urlToImage,
                    article.title,
                    article.description
                )
            )
        }
        _uiState.update {
            it.copy(dashboardListItems = dashboardListItems, state = UiState.State.Data)
        }
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

    fun submitEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.emit(event)
    }

    sealed interface UiEvent {
        data class ListItemClicked(val model: DashboardListItemModel) : UiEvent
        object UserReturnedToScreen : UiEvent
    }

    sealed interface UiAction {
        data class NavigateToArticleDetails(val model: DashboardListItemModel) : UiAction
    }
}