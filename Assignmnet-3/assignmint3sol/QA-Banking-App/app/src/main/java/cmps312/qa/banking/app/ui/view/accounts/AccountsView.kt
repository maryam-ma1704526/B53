package cmps312.qa.banking.app.ui.view.accounts

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.qa.banking.app.models.AccountModel
import cmps312.qa.banking.app.ui.viewmodel.BankingViewModel

@Composable
fun AccountsView(onEditAccountClicked: (AccountModel) -> Unit, onAddAccountClicked: () -> Unit) {

    val filtersList = listOf("Filter by All", "Filter by Current", "Filter by Saving")
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter = filtersList[0]

    val bankingViewModel =
        viewModel<BankingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Accounts") })
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    onAddAccountClicked()
                },
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) {

        Column(
            modifier = Modifier.padding(15.dp)
        ) {

            // Spinner
            Button(
                onClick = { expanded = !expanded }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Text(selectedFilter)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                filtersList.forEach { label ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        selectedFilter = label
                        when (selectedFilter.trim()) {
                            filtersList[0] -> bankingViewModel.getAccounts()
                            filtersList[1] -> bankingViewModel.getAccounts("current")
                            filtersList[2] -> bankingViewModel.getAccounts("saving")
                        }
                    }) {
                        Text(text = label)
                    }
                }
            }

            // Accounts list
            LazyColumn {
                itemsIndexed(items = bankingViewModel.accounts) { _, model ->
                    AccountCard(model, onDeleteClicked = {
                        bankingViewModel.deleteAccount(model.accountNo)
                    }, onEditAccountClicked = {
                        onEditAccountClicked(model)
                    })
                }
            }

        }

    }

}

@Composable
fun AccountCard(
    accountModel: AccountModel,
    onDeleteClicked: () -> Unit,
    onEditAccountClicked: () -> Unit
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row {
                Text(text = accountModel.name, Modifier.weight(1.5f))
                IconButton(onClick = { onDeleteClicked() }, Modifier.weight(0.5f)) {
                    Icon(
                        imageVector = Icons.Filled.Delete, contentDescription = null,
                        Modifier.weight(0.5f)
                    )
                }
            }
            Text(text = accountModel.accountNo)
            Text(text = accountModel.acctType)
            Row {
                Text(text = "${accountModel.balance} QR", Modifier.weight(1.5f))
                IconButton(onClick = { onEditAccountClicked() }, Modifier.weight(0.5f)) {
                    Icon(
                        imageVector = Icons.Filled.Edit, contentDescription = null,
                        Modifier.weight(0.5f)
                    )
                }
            }
        }
    }
}