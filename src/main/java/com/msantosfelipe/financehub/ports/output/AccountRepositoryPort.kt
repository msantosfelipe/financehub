package com.msantosfelipe.financehub.ports.output

import com.msantosfelipe.financehub.domain.model.Account
import java.util.UUID

interface AccountRepository {
    suspend fun create(account: Account): UUID

    suspend fun update(account: Account): Account

    suspend fun getAll(): List<Account>

    suspend fun getByName(name: String): Account?
}

class AccountNotFoundException(msg: String) : RuntimeException(msg)
