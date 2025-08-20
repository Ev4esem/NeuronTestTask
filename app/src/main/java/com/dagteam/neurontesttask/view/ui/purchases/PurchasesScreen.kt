package com.dagteam.neurontesttask.view.ui.purchases

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dagteam.neurontesttask.R
import com.dagteam.neurontesttask.domain.entities.Purchase
import com.dagteam.neurontesttask.view.ui.component.BackButton
import com.dagteam.neurontesttask.view.ui.theme.CardColor
import com.dagteam.neurontesttask.view.ui.theme.NeuronTestTaskTheme

@Composable
fun PurchasesScreen(
    uiState: UiState,
    modifier: Modifier = Modifier,
    onIntent: (PurchasesIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        BackButton(
            onClick = { onIntent(PurchasesIntent.PressOnBack) }
        )
        when {
            uiState.isLoading -> {
                LoadingState(modifier = Modifier.fillMaxSize())
            }

            uiState.error != null -> {
                ErrorState(
                    error = uiState.error,
                    modifier = Modifier.fillMaxSize()
                )
            }

            uiState.groupedPurchases.isEmpty() -> {
                EmptyState(modifier = Modifier.fillMaxSize())
            }

            else -> {
                PurchasesList(
                    groupedPurchases = uiState.groupedPurchases,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun PurchasesList(
    groupedPurchases: Map<String, List<Purchase>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedPurchases.forEach { (date, purchases) ->
            item(key = date) {
                DateHeader(date = date)
            }
            items(purchases) { purchase ->
                PurchaseItems(purchase = purchase)
            }
        }
    }
}

@Composable
private fun DateHeader(
    date: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )
    }
}

@Composable
private fun PurchaseItems(
    purchase: Purchase,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        purchase.name.forEach { item ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardColor
                )
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.purchases_loading),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorState(
    error: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.purchases_error_title),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.purchases_empty_title),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.purchases_empty_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun PurchasesScreenPreview() {
    NeuronTestTaskTheme {
        PurchasesScreen(
            uiState = UiState(
                groupedPurchases = mapOf(
                    "2023-12-20" to listOf(
                        Purchase(
                            date = "2023-12-20",
                            name = listOf("Молоко", "Хлеб", "Сыр")
                        ),
                        Purchase(
                            date = "2023-12-20", 
                            name = listOf("Кофе")
                        )
                    ),
                    "2023-12-19" to listOf(
                        Purchase(
                            date = "2023-12-19",
                            name = listOf("Яблоки", "Апельсины")
                        )
                    )
                )
            ),
            onIntent = {}
        )
    }
}

@Preview
@Composable
private fun PurchasesScreenLoadingPreview() {
    NeuronTestTaskTheme {
        PurchasesScreen(
            uiState = UiState(isLoading = true),
            onIntent = {}
        )
    }
}

@Preview
@Composable
private fun PurchasesScreenEmptyPreview() {
    NeuronTestTaskTheme {
        PurchasesScreen(
            uiState = UiState(),
            onIntent = {}
        )
    }
}

@Preview
@Composable
private fun PurchasesScreenErrorPreview() {
    NeuronTestTaskTheme {
        PurchasesScreen(
            uiState = UiState(error = "Не удалось загрузить покупки"),
            onIntent = {}
        )
    }
}