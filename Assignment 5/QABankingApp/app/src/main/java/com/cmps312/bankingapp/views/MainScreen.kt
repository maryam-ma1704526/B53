package com.cmps312.bankingapp.views

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cmps312.bankingapp.common.getCurrentRoute
import com.cmps312.bankingapp.models.Account
import com.cmps312.bankingapp.viewModels.AccountsViewModel


@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun MainScreen() {
    val navHostController = rememberNavController()
    val accountsViewModel =
        viewModel<AccountsViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    Scaffold(
        topBar = { TopBar(navHostController) },
        bottomBar = { BottomBar(navHostController) },
        floatingActionButton = {
            if (getCurrentRoute(navHostController) !== Screen.AccountForm.route) {
                IconButton(
                    onClick = {
                        accountsViewModel.selectedAccount = Account()
                        navHostController.navigate(Screen.AccountForm.route)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            Color.Cyan
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Floating Button",
                    )
                }
            }
        }
    ) {
        AppNavigation(navHostController)
    }
}

@Composable
fun TopBar(navHostController: NavHostController) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (getCurrentRoute(navHostController) === Screen.AccountForm.route) {
            IconButton(onClick = { navHostController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back Arrow"
                )
            }
        }

        Icon(
            imageVector = Icons.Default.Business,
            contentDescription = "Bank",
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Q Banking App",
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun BottomBar(navHostController: NavHostController) {
    var bottomNavItems = listOf(
        Screen.AccountList,
        Screen.Transaction

    )
    val currentRoute = navHostController
        .currentBackStackEntryAsState()?.value?.destination?.route

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