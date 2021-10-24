package cmps312.qa.banking.app.webapi

import cmps312.qa.banking.app.models.AccountModel
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*

class QaBankService : BankService {

    private val baseUrl = "https://employee-bank-app.herokuapp.com/api"

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun getAccounts(type: String?): List<AccountModel> {
        val url = if (type == null) "$baseUrl/accounts" else "$baseUrl/accounts?Type=$type"
        return client.get(url)
    }

    override suspend fun deleteAccount(accountNo: String) {
        val url = "$baseUrl/accounts/$accountNo"
        return client.delete(url)
    }

    override suspend fun addAccount(accountModel: AccountModel): AccountModel {
        return client.post {
            url("$baseUrl/accounts")
            contentType(ContentType.Application.Json)
            body = accountModel
        }
    }

    override suspend fun updateAccount(accountModel: AccountModel): AccountModel {
        val url = "$baseUrl/accounts/${accountModel.accountNo}"
        return client.put {
            url(url)
            contentType(ContentType.Application.Json)
            body = accountModel
        }
    }

}