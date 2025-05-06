package com.msantosfelipe.financehub.account.ports.output

import com.msantosfelipe.financehub.account.domain.model.Account
import java.util.UUID

interface AccountRepository {
    suspend fun create(account: Account): UUID

    suspend fun update(account: Account): Account

    suspend fun getAll(): List<Account>

    suspend fun getById(id: UUID): Account

    suspend fun getByName(name: String): Account
}

class AccountNotFoundException(message: String?) : RuntimeException(message)

class AccountAlreadyExistsException(message: String?) : RuntimeException(message)
