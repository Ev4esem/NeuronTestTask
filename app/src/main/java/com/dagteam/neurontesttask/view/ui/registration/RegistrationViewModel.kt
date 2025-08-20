package com.dagteam.neurontesttask.view.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dagteam.neurontesttask.domain.entities.UserData
import com.dagteam.neurontesttask.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AccountRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            val userData = repository.getUserData().first()
            _uiState.value = RegistrationUiState(
                code = userData.password,
                name = userData.name,
                surname = userData.surname,
                participantNumber = userData.creditNumber
            ).validateFields()
        }
    }

    private val _news = Channel<RegistrationNews>()
    val news = _news.receiveAsFlow()

    fun onIntent(intent: RegistrationIntent) {
        when (intent) {
            is RegistrationIntent.ChangeCode -> {
                updateState { it.copy(code = intent.code) }
            }

            is RegistrationIntent.ChangeName -> {
                updateState { it.copy(name = intent.name) }
            }

            is RegistrationIntent.ChangeSurname -> {
                updateState { it.copy(surname = intent.surname) }
            }

            is RegistrationIntent.ChangeNumber -> {
                val digits = intent.number.replace(" ", "").take(16)
                updateState { it.copy(participantNumber = digits) }
            }

            is RegistrationIntent.PressOnContinue -> {
                if (uiState.value.isContinueButtonEnabled) {
                    handlePressOnContinue()
                }
            }

            is RegistrationIntent.PressOnBack -> {
                viewModelScope.launch {
                    _news.send(RegistrationNews.NavigationToAccountScreen)
                }
            }
        }
    }

    private fun handlePressOnContinue() {
        val userData = UserData(
            name = _uiState.value.name,
            surname = _uiState.value.surname,
            password = _uiState.value.code,
            creditNumber = _uiState.value.participantNumber
        )
        viewModelScope.launch {
            repository.setUserData(userData)
            _news.send(RegistrationNews.NavigationToAccountScreen)
        }
    }

    private fun updateState(update: (RegistrationUiState) -> RegistrationUiState) {
        _uiState.value = update(_uiState.value).validateFields()
    }

    private fun RegistrationUiState.validateFields(): RegistrationUiState {
        val isCodeValid = code.isNotBlank()
        val isNameValid = name.isNotBlank()
        val isSurnameValid = surname.isNotBlank()
        val isParticipantNumberValid =
            participantNumber.length == 16 && participantNumber.all { it.isDigit() }

        return copy(
            isCodeValid = isCodeValid,
            isNameValid = isNameValid,
            isSurnameValid = isSurnameValid,
            isParticipantNumberValid = isParticipantNumberValid,
            isContinueButtonEnabled = isCodeValid && isNameValid && isSurnameValid && isParticipantNumberValid
        )
    }
}

data class RegistrationUiState(
    val code: String = "",
    val name: String = "",
    val surname: String = "",
    val participantNumber: String = "",
    val isCodeValid: Boolean = false,
    val isNameValid: Boolean = false,
    val isSurnameValid: Boolean = false,
    val isParticipantNumberValid: Boolean = false,
    val isContinueButtonEnabled: Boolean = false
)

sealed interface RegistrationIntent {
    data class ChangeNumber(val number: String) : RegistrationIntent
    data class ChangeCode(val code: String) : RegistrationIntent
    data class ChangeName(val name: String) : RegistrationIntent
    data class ChangeSurname(val surname: String) : RegistrationIntent
    data object PressOnContinue : RegistrationIntent
    data object PressOnBack : RegistrationIntent
}

sealed interface RegistrationNews {
    data object NavigationToAccountScreen : RegistrationNews
}