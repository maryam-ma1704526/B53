package com.cmps312.bankingapp.viewModels

import android.app.Application
import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.cmps312.bankingapp.common.generateRandomAccounts
import com.cmps312.bankingapp.models.Account
import com.cmps312.bankingapp.models.Transaction

class AccountsViewModel(appContext: Application) : AndroidViewModel(appContext) {
    var selectedAccount by mutableStateOf(Account())
    var accounts by mutableStateOf(mutableStateListOf<Account>().toTypedArray().toList())
    var currentFilter by mutableStateOf("All")


    init {
        accounts = generateRandomAccounts()
    }

    //Todo Update this method, and connect it to the accountRepo
    fun getAccounts(acctType: String) = if (acctType == "All") accounts else accounts.filter { it.acctType == acctType }

    //Todo Update this method, and connect it to the accountRepo
    fun getAccount(accountNo: String): List<Account> =
        accounts.filter { it.accountNo == accountNo }

    //Todo Update this method, this was done just to help you with the design
    fun addAccount(account: Account) = accounts.let {
        accounts = it + account
    }

    //Todo Update this method, this was done just to help you with the design
    fun editAccount(accountNo: String, account: Account) {
        val index = accounts.indexOfFirst { acc -> acc.accountNo == accountNo }
        account.apply {
            accounts[index].acctType = acctType
            accounts[index].balance = balance
            accounts[index].name = name
        }
    }

    //Todo Update this method, this was done just to help you with the design
    fun addTransaction(transaction: Transaction) {
        val index = accounts.indexOfFirst { acc -> acc.accountNo == transaction.accountNo }
        if (transaction.type == "Withdraw") {
            accounts[index].balance -= transaction.amount
        } else {
            accounts[index].balance += transaction.amount
        }
    }

    //Todo Update this method, this was done just to help you with the design
    @RequiresApi(Build.VERSION_CODES.N)
    fun deleteAccount(accountNo: String) = accounts.let {
        accounts = it - accounts.first { it.accountNo == accountNo }
    }
}
