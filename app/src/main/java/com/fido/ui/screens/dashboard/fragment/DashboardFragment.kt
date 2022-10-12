package com.fido.ui.screens.dashboard.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fido.databinding.FragmentDashboardBinding
import com.fido.model.ui_models.BaseDashboardListItemModel
import com.fido.ui.screens.dashboard.viewmodel.DashboardViewModel
import com.fido.model.ui_models.DashboardListItemModel
import com.fido.ui.screens.dashboard.adapter.TeslaNewsArticleAdapter
import com.fido.utils.extensions.launchAndRepeatWithViewLifecycle
import com.fido.utils.extensions.setVerticalAdapter
import org.koin.android.ext.android.get

class DashboardFragment : Fragment() {

    //UI
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var adapter: TeslaNewsArticleAdapter

    //Dependency Injection
    private val dashboardViewModel = get<DashboardViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
        initAdapter()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initAdapter() {
        adapter = TeslaNewsArticleAdapter()
    }

    private fun observeUiState() = launchAndRepeatWithViewLifecycle {

        dashboardViewModel.uiState.collect { uiState ->
            when (uiState.state) {
                DashboardViewModel.UiState.State.Initial -> {
                    updateAdapter(uiState.dashboardListItems)
                }
                DashboardViewModel.UiState.State.Data -> {
                    updateAdapter(uiState.dashboardListItems)
                }
                DashboardViewModel.UiState.State.Error -> {
                    Toast.makeText(requireContext(), uiState.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun updateAdapter(dashboardListItems: List<BaseDashboardListItemModel>) {
        binding.fragmentMainRecyclerview.setVerticalAdapter(requireContext(), adapter)
        adapter.submitList(dashboardListItems)
    }

}