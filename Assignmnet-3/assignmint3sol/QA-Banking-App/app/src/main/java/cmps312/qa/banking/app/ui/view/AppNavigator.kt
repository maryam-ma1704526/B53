package cmps312.qa.banking.app.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import cmps312.qa.banking.app.ui.view.accounts.AccountsView
import cmps312.qa.banking.app.ui.view.accounts.ModifyAccountView
import cmps312.qa.banking.app.ui.view.transactions.TransactionsView

@Composable
fun AppNavigator(navHostController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Accounts.route,
        //Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues)
    ) {

        composable(route = Screen.Accounts.route) {
            AccountsView(onEditAccountClicked = { model ->
                navHostController.navigate("${Screen.ModifyAccount.route}/${model.accountNo}")
            }, onAddAccountClicked = {
                navHostController.navigate(Screen.ModifyAccount.route)
            })
        }

        composable(route = Screen.Transactions.route) {
            TransactionsView()
        }

        composable(route = Screen.ModifyAccount.route + "/{accountNo}",
            arguments = listOf(
                navArgument("accountNo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ModifyAccountView(
                backStackEntry.arguments?.getString("accountNo") ?: "",
                onNavigateBack = { navHostController.navigateUp() })
        }

        composable(route = Screen.ModifyAccount.route) {
            ModifyAccountView(null, onNavigateBack = { navHostController.navigateUp() })
        }

    }
}