package com.msantosfelipe.financehub.domains.accounts.ports.input

import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import java.util.UUID

interface AccountServicePort {
    suspend fun createAccount(account: Account): UUID

    suspend fun updateAccount(account: Account): Account

    suspend fun getAllAccounts(): List<Account>

    suspend fun getAccountById(uuid: UUID): Account

    suspend fun getAccountByName(searchName: String): Account
}
