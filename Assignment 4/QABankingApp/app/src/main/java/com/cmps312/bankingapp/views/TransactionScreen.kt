package com.cmps312.bankingapp.views

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmps312.bankingapp.models.Transaction
import com.cmps312.bankingapp.viewModels.AccountsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun TransactionScreen(onTransaction: () -> Unit) {
    val accountsViewModel =
        viewModel<AccountsViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var updatedBalance by remember {
        mutableStateOf(0.0)
    }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }

    var fromAccount by remember { mutableStateOf("") }
    var balance by remember { mutableStateOf(0.0) }

    var transactionType by remember { mutableStateOf("") }
    val options = listOf("Withdraw", "Deposit")
    var amountTrypeExpandable by remember {
        mutableStateOf(false)
    }
    var transactionTypeExpandable by remember {
        mutableStateOf(false)
    }
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
            Text(text = "Add Transaction", fontWeight = FontWeight.Bold, fontSize = 26.sp)
            Divider()
            Row(
                modifier = Modifier
                    .clickable {
                        transactionTypeExpandable = !transactionTypeExpandable
                    }
                    .fillMaxWidth()


            ) {
                Text(text = "Select Account")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "type"
                )
            }
            DropdownMenu(
                expanded = transactionTypeExpandable,
                onDismissRequest = { transactionTypeExpandable = false }) {
                accountsViewModel.accounts.forEach { account ->
                    DropdownMenuItem(onClick = {
                        fromAccount = account.accountNo
                        balance = account.balance!!
                        name = account.name
                        type = account.acctType
                        transactionTypeExpandable = false

                    }) {
                        Text(text = "$account", fontWeight = FontWeight.Bold)

                    }
                }
            }
            Column {
                Text(text = "Account Number :$fromAccount")
                Text(text = "Balance: $balance")
            }
            Row(
                modifier = Modifier
                    .clickable {
                        amountTrypeExpandable = !amountTrypeExpandable
                    }
                    .fillMaxWidth()


            ) {
                Text(text = "Select Transaction Type")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "filter type"
                )
            }
            DropdownMenu(
                expanded = amountTrypeExpandable,
                onDismissRequest = { amountTrypeExpandable = false }) {
                options.forEach { option ->
                    DropdownMenuItem(onClick = {
                        transactionType = option
                        amountTrypeExpandable = false

                    }) {
                        Text(text = "$option", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Text(text = "Transaction Type: $transactionType")
            OutlinedTextField(value = amount,
                onValueChange = { amount = it },
                label = { Text(text = "Enter amount") })
            Button(onClick = {
                val transaction = Transaction(transactionType, amount.toDouble(), fromAccount)
                accountsViewModel.addTransaction(transaction)
                onTransaction()
            }) {
                Text(text = "Submit")
            }
        }


    }


}