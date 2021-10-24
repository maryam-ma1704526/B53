package cmps312.qa.banking.app.ui.view.accounts

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cmps312.qa.banking.app.common.displayMessage
import cmps312.qa.banking.app.models.AccountModel
import cmps312.qa.banking.app.ui.viewmodel.BankingViewModel

@Composable
fun ModifyAccountView(accountNo: String? = null, onNavigateBack: () -> Unit) {

    val accountTypes = listOf("Saving", "Current")
    var expanded by remember { mutableStateOf(false) }
    var selectedAccountType = accountTypes[0]

    var nameText by remember { mutableStateOf("") }
    var accountNoText by remember { mutableStateOf(accountNo ?: "") }
    var balanceText by remember { mutableStateOf("") }

    val bankingViewModel =
        viewModel<BankingViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current

    bankingViewModel.getAccountByNo(accountNo)?.let {
        nameText = it.name
        accountNoText = it.accountNo
        balanceText = it.balance.toString()
        selectedAccountType = it.acctType
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Account Editor") })
        }
    ) {

        Column(
            Modifier.padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = if (accountNo == null || accountNo.isEmpty()) "Add Account" else "Edit Account",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Name:", Modifier.weight(0.8f))
                TextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    modifier = Modifier.weight(1.2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Account No:", Modifier.weight(0.8f))
                TextField(
                    value = accountNoText,
                    onValueChange = { accountNoText = it },
                    modifier = Modifier.weight(1.2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                    enabled = (accountNo == null || accountNo.isEmpty())
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp)
            ) {
                Text(text = "Account Type", Modifier.weight(0.8f))
                // Spinner
                Button(
                    onClick = { expanded = !expanded },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.weight(1.2f)
                ) {
                    Text(selectedAccountType)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    accountTypes.forEach { label ->
                        DropdownMenuItem(onClick = {
                            expanded = false
                            selectedAccountType = label
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Balance", Modifier.weight(0.8f))
                TextField(
                    value = balanceText,
                    onValueChange = { balanceText = it },
                    modifier = Modifier.weight(1.2f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Black
                    ),
                )
            }

            Row(Modifier.padding(top = 20.dp)) {

                Button(
                    onClick = {
                        onNavigateBack()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = {

                        if (nameText.trim().isEmpty()
                            || accountNoText.trim().isEmpty()
                            || balanceText.trim().isEmpty()
                            || selectedAccountType.trim().isEmpty()
                        ) {
                            displayMessage(context, "Please enter all fields.")
                        } else {
                            try {
                                val balanceDoubleForm = balanceText.trim().toDouble()
                                val model = AccountModel(
                                    accountNoText,
                                    nameText,
                                    selectedAccountType,
                                    balanceDoubleForm
                                )

                                if (accountNo == null || accountNo.isEmpty()) {
                                    bankingViewModel.createAccount(model)
                                } else {
                                    bankingViewModel.updateAccount(model)
                                }

                                onNavigateBack()
                            } catch (e: Exception) {
                                displayMessage(context, "Please enter valid balance.")
                            }
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Done")
                }

            }


        }
    }
}