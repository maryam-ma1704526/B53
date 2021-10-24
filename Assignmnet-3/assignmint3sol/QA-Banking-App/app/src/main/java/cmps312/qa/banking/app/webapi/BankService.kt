package cmps312.qa.banking.app.webapi

import cmps312.qa.banking.app.models.AccountModel

interface BankService {
    suspend fun getAccounts(type: String? = null): List<AccountModel>
    suspend fun deleteAccount(accountNo: String)
    suspend fun addAccount(accountModel: AccountModel): AccountModel
    suspend fun updateAccount(accountModel: AccountModel): AccountModel
}