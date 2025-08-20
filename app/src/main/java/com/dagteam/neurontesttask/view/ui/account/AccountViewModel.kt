package com.dagteam.neurontesttask.view.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dagteam.neurontesttask.domain.entities.UserData
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel() {

    private val _news = Channel<AccountNews>()
    val news = _news.receiveAsFlow()

    val uiState: StateFlow<UiState> = accountRepository.getUserData().map { userData ->
        UiState(
            userData = userData,
        )
    }.stateIn(
        scope = viewModelScope,
        started = Lazily,
        initialValue = UiState()
    )

    fun onIntent(intent: AccountIntent) {
        when (intent) {
            is AccountIntent.PressMyBuys -> {
                handlePressMyBuys()
            }
            is AccountIntent.PressRegistration -> {
                handlePressRegistration()
            }
        }
    }

    private fun handlePressMyBuys() {
        viewModelScope.launch {
            _news.send(AccountNews.NavigationToMyBuys)
        }
    }

    private fun handlePressRegistration() {
        viewModelScope.launch {
            _news.send(AccountNews.NavigationToRegistration)
        }
    }
}

data class UiState(
    val userData: UserData = UserData.Empty,
    val isEnterBiometry: Boolean = true,
    val isConfirmedEmail: Boolean = false,
    val phone: String = "+7 999 123-45-67",
    val email: String = "ev4esem@gmail.com",
)

sealed interface AccountIntent {
    data object PressMyBuys : AccountIntent
    data object PressRegistration : AccountIntent
}

sealed interface AccountNews {
    data object NavigationToMyBuys : AccountNews
    data object NavigationToRegistration : AccountNews
}