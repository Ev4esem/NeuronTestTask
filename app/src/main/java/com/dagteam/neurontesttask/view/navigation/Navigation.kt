package com.dagteam.neurontesttask.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dagteam.neurontesttask.NeuronApplication
import com.dagteam.neurontesttask.view.ui.account.AccountScreen
import com.dagteam.neurontesttask.view.ui.account.AccountViewModel
import com.dagteam.neurontesttask.view.ui.registration.RegistrationScreen
import com.dagteam.neurontesttask.view.ui.registration.RegistrationViewModel
import com.dagteam.neurontesttask.view.ui.purchases.PurchasesScreen
import com.dagteam.neurontesttask.view.ui.purchases.PurchasesViewModel
import com.dagteam.neurontesttask.view.ui.purchases.PurchasesNews
import com.dagteam.neurontesttask.view.ui.account.AccountNews
import com.dagteam.neurontesttask.view.ui.registration.RegistrationNews

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues
) {
    val application = LocalContext.current.applicationContext as NeuronApplication
    
    NavHost(
        navController = navController,
        startDestination = Screen.Account.route
    ) {
        composable(Screen.Account.route) {
            val viewModel: AccountViewModel = viewModel {
                AccountViewModel(application.accountRepository)
            }
            val uiState by viewModel.uiState.collectAsState()
            
            LaunchedEffect(Unit) {
                viewModel.news.collect { news ->
                    when (news) {
                        is AccountNews.NavigationToMyBuys -> {
                            navController.navigate(Screen.MyBuys.route)
                        }
                        is AccountNews.NavigationToRegistration -> {
                            navController.navigate(Screen.Registration.route)
                        }
                    }
                }
            }
            
            AccountScreen(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onIntent = viewModel::onIntent
            )
        }
        
        composable(Screen.Registration.route) {
            val viewModel: RegistrationViewModel = viewModel {
                RegistrationViewModel(application.accountRepository)
            }
            val uiState by viewModel.uiState.collectAsState()
            
            LaunchedEffect(Unit) {
                viewModel.news.collect { news ->
                    when (news) {
                        is RegistrationNews.NavigationToAccountScreen -> {
                            navController.navigate(Screen.Account.route) {
                                popUpTo(Screen.Account.route) { inclusive = true }
                            }
                        }
                    }
                }
            }
            
            RegistrationScreen(
                modifier = Modifier.padding(paddingValues),
                uiState = uiState,
                onIntent = viewModel::onIntent
            )
        }
        
        composable(Screen.MyBuys.route) {
            val viewModel: PurchasesViewModel = viewModel {
                PurchasesViewModel(application.accountRepository)
            }
            val uiState by viewModel.uiState.collectAsState()
            
            LaunchedEffect(Unit) {
                viewModel.news.collect { news ->
                    when (news) {
                        is PurchasesNews.NavigateToAccount -> {
                            navController.popBackStack()
                        }
                    }
                }
            }
            
            PurchasesScreen(
                uiState = uiState,
                modifier = Modifier.padding(paddingValues),
                onIntent = viewModel::onIntent
            )
        }
    }
}

sealed class Screen(val route: String) {
    data object Account : Screen("account")
    data object Registration : Screen("registration")
    data object MyBuys : Screen("my_buys")
}