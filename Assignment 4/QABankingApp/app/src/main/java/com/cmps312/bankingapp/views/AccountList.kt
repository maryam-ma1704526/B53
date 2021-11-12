package com.cmps312.bankingapp.views

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cmps312.bankingapp.models.Account
import com.cmps312.bankingapp.viewModels.AccountsViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun AccountList(onEdit: () -> Unit) {
    val accountsViewModel =
        viewModel<AccountsViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var selectedFilter by remember {
        mutableStateOf("All")
    }
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Dropdown(
            label = "Filter By",
            options = listOf("Saving", "Current", "All"),
            selectedOption = selectedFilter,
            onSelectionChange = {
                selectedFilter = it
                accountsViewModel.getAccounts(it)
                Log.d(ContentValues.TAG, it)
            }
        )
        LazyColumn {
            items(accountsViewModel.getAccounts(selectedFilter)) { account ->
                AccountCard(
                    account,
                    deleteAccount = { accountsViewModel.deleteAccount(it) },
                    editAccount = {
                        accountsViewModel.selectedAccount = it
                        onEdit()
                    })
            }
        }
    }
}

@Composable
fun AccountCard(
    account: Account,
    deleteAccount: (accountID: String) -> Unit,
    editAccount: (account: Account) -> Unit,
) {
    Card(
        elevation = 20.dp,
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
            ) {
                account.name?.let { Text(text = "Name: $it") }
                account.accountNo?.let { Text(text = "Account No: $it") }
                account.acctType?.let { Text(text = "Type: $it") }
                Text(text = "Account Balance: ${account.balance} QR")

            }
            Column {
                IconButton(onClick = { account.accountNo?.let { deleteAccount(it) } }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Button")
                }
                IconButton(onClick = { editAccount(account) }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Button")
                }
            }
        }
    }
}