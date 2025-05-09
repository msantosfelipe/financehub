package com.msantosfelipe.financehub.accounts.ports.output

import com.msantosfelipe.financehub.accounts.domain.model.Account
import java.util.UUID

interface AccountRepositoryPort {
    suspend fun create(account: Account): UUID

    suspend fun update(account: Account): Account

    suspend fun getAll(): List<Account>

    suspend fun getById(id: UUID): Account

    suspend fun getByName(name: String): Account
}

class AccountNotFoundException(message: String?) : RuntimeException(message)

class AccountAlreadyExistsException(message: String?) : RuntimeException(message)
