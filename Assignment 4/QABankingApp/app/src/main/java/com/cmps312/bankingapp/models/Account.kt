package com.cmps312.bankingapp.models

import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val accountNo: String = "",
    var name: String = "",
    var acctType: String = "",
    var balance: Double = 0.0,
)