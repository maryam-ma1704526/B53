package cmps312.qa.banking.app.ui.view.transactions

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun TransactionsView() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Transactions") })
        }
    ) {

    }
}