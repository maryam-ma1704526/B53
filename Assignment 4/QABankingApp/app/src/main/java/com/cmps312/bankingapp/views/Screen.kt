package com.cmps312.bankingapp.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Money
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object AccountForm : Screen("AccountForm", "Account Editor", icon = Icons.Outlined.List)
    object AccountList : Screen("AccountList", "Accounts List", icon = Icons.Outlined.List)
    object Transaction :
        Screen(route = "Transaction", title = "Transaction", icon = Icons.Outlined.Money)
}