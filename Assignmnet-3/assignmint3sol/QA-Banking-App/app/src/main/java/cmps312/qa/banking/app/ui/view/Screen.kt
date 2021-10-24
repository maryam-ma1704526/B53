package cmps312.qa.banking.app.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route : String, val title : String, val icon: ImageVector){
    object Accounts : Screen(route = "accounts", title = "Accounts", icon = Icons.Outlined.People)
    object Transactions : Screen(route = "transactions", title = "Transactions", icon = Icons.Outlined.Money)
    object ModifyAccount : Screen(route = "modifyAccount", title = "Account Editor", icon = Icons.Outlined.Edit)
}
