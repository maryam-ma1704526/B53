package com.cmps312.bankingapp.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navHostController, startDestination = Screen.AccountList.route) {
        composable(route = Screen.AccountList.route) {
            AccountList(onEdit = {
                navHostController.navigate(route = Screen.AccountForm.route)
            })
        }
        composable(route = Screen.AccountForm.route) {
            AccountForm(navHostController)
        }
        composable(route = Screen.Transaction.route) {
            TransactionScreen(onTransaction = { navHostController.navigate(Screen.AccountList.route) })
        }

    }
}