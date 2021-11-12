package com.cmps312.bankingapp.views

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.cmps312.bankingapp.models.Account
import com.cmps312.bankingapp.viewModels.AccountsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AccountForm(navHostController: NavHostController) {
    val accountsViewModel =
        viewModel<AccountsViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val selectedAccount = accountsViewModel.selectedAccount
    var accountName by remember {
        mutableStateOf(selectedAccount.name)
    }
    var accountNumber by remember {
        mutableStateOf(selectedAccount.accountNo)
    }
    var accountBalance by remember {
        mutableStateOf(selectedAccount.balance)
    }
    var accountType by remember {
        mutableStateOf(selectedAccount.acctType)
    }
    val accountTypes = listOf("Current", "Savings")

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(65.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = if (selectedAccount.accountNo?.isNotEmpty()!!) "Edit Account" else "Add Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Divider()
            OutlinedTextField(
                value = accountName ?: "",
                onValueChange = { accountName = it },
                placeholder = {
                    Text(
                        text = "Enter Account Name"
                    )
                })
            OutlinedTextField(
                value = accountNumber ?: "",
                onValueChange = { accountNumber = it },
                placeholder = {
                    Text(
                        text = "Enter Account Number"
                    )
                })
            accountType?.let {
                Dropdown(
                    label = "Select Account Type",
                    options = accountTypes,
                    selectedOption = it,
                    onSelectionChange = {
                        accountType = it
                    },
                )
            }

            OutlinedTextField(
                value = "$accountBalance",
                onValueChange = { accountBalance = it.toDouble() },
                placeholder = {
                    Text(
                        text = "Enter Account Balance"
                    )
                })
            Button(onClick = {
                if (selectedAccount.accountNo.isNotEmpty()) {
                    accountsViewModel.editAccount(
                        selectedAccount.accountNo,
                        Account(accountNumber, accountName, accountType, accountBalance)
                    )
                } else {
                    accountsViewModel.addAccount(
                        Account(
                            accountNumber,
                            accountName,
                            accountType,
                            accountBalance
                        )
                    )
                }
                navHostController.navigate(Screen.AccountList.route)
            }) {
                Text(text = "Submit")
            }
        }
    }

}
