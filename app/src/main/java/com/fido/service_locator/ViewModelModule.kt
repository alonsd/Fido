package com.fido.service_locator

import com.fido.ui.screens.dashboard.viewmodel.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
}