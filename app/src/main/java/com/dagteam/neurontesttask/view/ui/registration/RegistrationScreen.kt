package com.dagteam.neurontesttask.view.ui.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dagteam.neurontesttask.R
import com.dagteam.neurontesttask.view.ui.component.BackButton
import com.dagteam.neurontesttask.view.ui.theme.BackgroundColor
import com.dagteam.neurontesttask.view.ui.theme.ButtonDisabledColor
import com.dagteam.neurontesttask.view.ui.theme.CardColor
import com.dagteam.neurontesttask.view.ui.theme.NeuronTestTaskTheme

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    uiState: RegistrationUiState,
    onIntent: (RegistrationIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        BackButton(
            onClick = { onIntent(RegistrationIntent.PressOnBack) }
        )
        
        Text(
            text = stringResource(R.string.title_for_card_registration_for_client_bank),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        InputField(
            value = formatCardNumber(uiState.participantNumber),
            onValueChange = {
                onIntent(RegistrationIntent.ChangeNumber(it))
            },
            label = stringResource(R.string.registration_participant_number_label),
            placeholder = stringResource(R.string.registration_participant_number_placeholder),
            keyboardType = KeyboardType.Number,
            isError = !uiState.isParticipantNumberValid && uiState.participantNumber.isNotEmpty()
        )

        InputField(
            value = uiState.code,
            onValueChange = { onIntent(RegistrationIntent.ChangeCode(it)) },
            label = stringResource(R.string.registration_code_label),
            placeholder = stringResource(R.string.registration_code_placeholder),
            isError = !uiState.isCodeValid && uiState.code.isNotEmpty()
        )
        
        InputField(
            value = uiState.name,
            onValueChange = { onIntent(RegistrationIntent.ChangeName(it)) },
            label = stringResource(R.string.registration_name_label),
            placeholder = stringResource(R.string.registration_name_placeholder),
            isError = !uiState.isNameValid && uiState.name.isNotEmpty()
        )
        
        InputField(
            value = uiState.surname,
            onValueChange = { onIntent(RegistrationIntent.ChangeSurname(it)) },
            label = stringResource(R.string.registration_surname_label),
            placeholder = stringResource(R.string.registration_surname_placeholder),
            isError = !uiState.isSurnameValid && uiState.surname.isNotEmpty()
        )
        
        Text(
            text = stringResource(R.string.registration_privacy_policy_text),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            textAlign = TextAlign.Start,
        )
        
        Button(
            onClick = { onIntent(RegistrationIntent.PressOnContinue) },
            enabled = uiState.isContinueButtonEnabled,
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White,
                disabledContentColor = Color.White,
                disabledContainerColor = ButtonDisabledColor,
            )
        ) {
            Text(
                text = stringResource(R.string.registration_continue_button),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                cursorColor = Color.White,
                errorTextColor = Color.Red,
                errorBorderColor = Color.Red,
                focusedContainerColor = CardColor,
                unfocusedContainerColor = CardColor,
                errorContainerColor = CardColor,

            ),
            shape = MaterialTheme.shapes.large,
            isError = isError,
            singleLine = true
        )
        Text(
            text = if(isError) stringResource(R.string.registration_incorrect_data_error) else label,
            style = MaterialTheme.typography.bodyMedium,
            color = if(isError) Color.Red else Color.Gray,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

private fun formatCardNumber(number: String): String {
    return number.chunked(4).joinToString(" ")
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    NeuronTestTaskTheme {
        RegistrationScreen(
            uiState = RegistrationUiState(
                code = "",
                name = "",
                surname = "",
                participantNumber = "",
                isCodeValid = false,
                isNameValid = false,
                isSurnameValid = false,
                isParticipantNumberValid = false,
                isContinueButtonEnabled = false
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "With Data")
@Composable
fun RegistrationScreenWithDataPreview() {
    NeuronTestTaskTheme {
        RegistrationScreen(
            uiState = RegistrationUiState(
                code = "12345",
                name = "Иван",
                surname = "Иванов",
                participantNumber = "1234567890123456",
                isCodeValid = true,
                isNameValid = true,
                isSurnameValid = true,
                isParticipantNumberValid = true,
                isContinueButtonEnabled = true
            ),
            onIntent = {}
        )
    }
}

@Preview(showBackground = true, name = "With Errors")
@Composable
fun RegistrationScreenWithErrorsPreview() {
    NeuronTestTaskTheme {
        RegistrationScreen(
            uiState = RegistrationUiState(
                code = "123",
                name = "",
                surname = "И",
                participantNumber = "123456",
                isCodeValid = false,
                isNameValid = false,
                isSurnameValid = false,
                isParticipantNumberValid = false,
                isContinueButtonEnabled = false
            ),
            onIntent = {}
        )
    }
}