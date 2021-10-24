package cmps312.qa.banking.app.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cmps312.qa.banking.app.models.AccountModel
import cmps312.qa.banking.app.webapi.QaBankService
import kotlinx.coroutines.launch

class BankingViewModel(appContext: Application) : AndroidViewModel(appContext) {

    private val bankService = QaBankService()

    val accounts = mutableStateListOf<AccountModel>()

    init {
        getAccounts()
    }

    fun getAccounts(type: String? = null) = viewModelScope.launch {
        updateAccounts(bankService.getAccounts(type))
    }

    fun deleteAccount(accountNo: String) = viewModelScope.launch {
        bankService.deleteAccount(accountNo)
        updateAccounts(accounts.filter { !it.accountNo.equals(accountNo, false) })
    }

    private fun updateAccounts(list: List<AccountModel>) {
        accounts.clear()
        accounts.addAll(list)
    }

    fun getAccountByNo(accountNo: String?): AccountModel? {
        for (i in 0 until accounts.size) {
            if (accounts[i].accountNo == accountNo) {
                return accounts[i]
            }
        }
        return null
    }

    fun createAccount(accountModel: AccountModel) = viewModelScope.launch {
        bankService.addAccount(accountModel).let {
            accounts.add(it)
        }
    }

    fun updateAccount(accountModel: AccountModel) = viewModelScope.launch {
        bankService.updateAccount(accountModel).let { model ->
            updateAccounts(accounts.filter { !it.accountNo.equals(model.accountNo, false) })
            accounts.add(model)
        }
    }

}


