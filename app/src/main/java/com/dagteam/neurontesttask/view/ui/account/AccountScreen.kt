package com.dagteam.neurontesttask.view.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dagteam.neurontesttask.R
import com.dagteam.neurontesttask.domain.entities.UserData
import com.dagteam.neurontesttask.view.ui.component.BackButton
import com.dagteam.neurontesttask.view.ui.theme.BackgroundColor
import com.dagteam.neurontesttask.view.ui.theme.CardColor

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onIntent: (AccountIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(BackgroundColor)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            BackButton(
                modifier = Modifier.padding(bottom = 40.dp),
                onClick = { /* no op */ }
            )
        }
        Box(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Header(
                name = uiState.userData.name,
                surname = uiState.userData.surname,
                phone = uiState.phone,
            )
        }
        BlockMyBuys(
            onClick = { onIntent(AccountIntent.PressMyBuys) }
        )
        BlockSettingWithSubtitle(
            modifier = Modifier.padding(top = 32.dp),
            email = uiState.email,
            isConfirmedEmail = uiState.isConfirmedEmail,
            onClick = { /* no op */ }
        )
        BlockSettingWithChip(
            title = stringResource(R.string.title_for_card_enter_for_biometry),
            isChecked = uiState.isEnterBiometry,
            modifier = Modifier.padding(top = 10.dp),
            onCheckedChange = {  },
            onClick = {  },
        )
        BlockSetting(
            modifier = Modifier.padding(top = 10.dp),
            title = stringResource(R.string.title_for_card_change_password),
            onClick = { /* no op */ }
        )
        BlockSetting(
            modifier = Modifier.padding(top = 10.dp),
            title = stringResource(R.string.title_for_card_registration_for_client_bank),
            onClick = { onIntent(AccountIntent.PressRegistration) }
        )
        BlockSetting(
            modifier = Modifier.padding(top = 10.dp),
            title = stringResource(R.string.title_for_card_language),
            subtitle = "русский",
            onClick = { /* no op */ }
        )
    }
}


@Composable
fun BlockMyBuys(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.title_my_buys),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = CardColor,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .background(Color.Blue, CircleShape)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun BlockSettingWithSubtitle(
    email: String,
    isConfirmedEmail: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.title_settings),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick,
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = CardColor,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.title_for_card_email),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = email,
                            color = Color.White
                        )
                        if (!isConfirmedEmail) {
                            Text(
                                text = stringResource(R.string.title_for_card_unnecessary_confirm_email),
                                color = Color.Red
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        tint = Color.White,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun BlockSettingWithChip(
    title: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = CardColor,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
            )
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color.Red,
                    uncheckedThumbColor = Color.Gray,
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
    }
}

@Composable
fun BlockSetting(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = CardColor,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                subtitle?.let {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun Header(
    name: String,
    surname: String,
    phone: String,
    modifier: Modifier = Modifier,
) {
    val text = buildString {
        append(name)
        append("\n")
        append(surname)
    }
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
            )
            Icon(
                imageVector = Icons.Default.Edit,
                tint = Color.White,
                contentDescription = null,
            )
        }
        Text(
            text = phone,
            color = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


@Preview
@Composable
fun AccountScreenPreview() {
    AccountScreen(
        uiState = UiState(
            userData = UserData(
                name = "John",
                surname = "Doe",
                password = "password123",
                creditNumber = "1234-5678-9012-3456"
            )
        ),
        onIntent = {}
    )
}