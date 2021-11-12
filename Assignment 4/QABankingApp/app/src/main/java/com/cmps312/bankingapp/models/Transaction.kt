package com.cmps312.bankingapp.models

import kotlinx.serialization.Serializable

@Serializable
class Transaction(
    var type: String,
    var amount: Double,
    var accountNo: String,
    var id: Int = 0,
)
