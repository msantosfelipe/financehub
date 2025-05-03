package com.msantosfelipe.financehub.ports.input

import com.msantosfelipe.financehub.domain.model.Account
import java.util.UUID

interface AccountServicePort {
    suspend fun createAccount(account: Account): UUID
    suspend fun updateAccount(account: Account): Account
    suspend fun getAllAccounts(): List<Account>
    suspend fun getAccountByName(name: String): Account?
}