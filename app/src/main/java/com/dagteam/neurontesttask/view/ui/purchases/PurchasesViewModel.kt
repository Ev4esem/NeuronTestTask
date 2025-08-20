package com.dagteam.neurontesttask.view.ui.purchases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dagteam.neurontesttask.domain.GetMyBuysUseCase
import com.dagteam.neurontesttask.domain.entities.Purchase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchasesViewModel @Inject constructor(
    private val getMyBuysUseCase: GetMyBuysUseCase,
) : ViewModel() {

    private val _news = Channel<PurchasesNews>()
    val news = _news.receiveAsFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadPurchases()
    }

    fun onIntent(intent: PurchasesIntent) {
        when (intent) {
            is PurchasesIntent.PressOnBack -> {
                viewModelScope.launch {
                    _news.send(PurchasesNews.NavigateToAccount)
                }
            }
        }
    }

    private fun loadPurchases() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                val groupedPurchases = getMyBuysUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    groupedPurchases = groupedPurchases
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = false,
    val groupedPurchases: Map<String, List<Purchase>> = emptyMap(),
    val error: String? = null
)

sealed interface PurchasesIntent {
    data object PressOnBack : PurchasesIntent
}

sealed interface PurchasesNews {
    data object NavigateToAccount : PurchasesNews
}