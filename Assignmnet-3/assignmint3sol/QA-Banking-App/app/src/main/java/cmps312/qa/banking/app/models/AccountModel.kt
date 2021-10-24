package cmps312.qa.banking.app.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountModel(
    val accountNo: String,
    val name: String,
    val acctType: String,
    val balance: Double
)
