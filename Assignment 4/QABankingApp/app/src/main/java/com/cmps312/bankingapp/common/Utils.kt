package com.cmps312.bankingapp.common

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cmps312.bankingapp.models.Account


fun displayMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

//Todo Replace with Databse
fun generateRandomAccounts() = mutableListOf<Account>(
    Account("10001", "Abdulahi", "Saving", 10000.0),
    Account("10005", "Zahra", "Saving", 60000.0),
    Account("10006", "ZamZam", "Current", 10000.0))
