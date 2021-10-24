package cmps312.qa.banking.app.ui.view

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import cmps312.qa.banking.app.common.getCurrentRoute
import cmps312.qa.banking.app.ui.theme.QABankingAppTheme

@Composable
fun MainView() {
    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navHostController) }
    ) { paddingValues ->
        AppNavigator(navHostController, paddingValues)
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    val bottomNavItems = listOf(
        Screen.Accounts,
        Screen.Transactions
    )
    val currentRoute = getCurrentRoute(navHostController)
    BottomNavigation {
        bottomNavItems.forEach { screen ->
            BottomNavigationItem(
                selected = currentRoute == screen.route,
                onClick = { navHostController.navigate(screen.route) },
                icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
                label = { Text(text = screen.title) },
                alwaysShowLabel = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    QABankingAppTheme {
        MainView()
    }
}