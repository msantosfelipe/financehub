package com.msantosfelipe.financehub.domains.accounts.ports.output

import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import java.util.UUID

interface AccountRepositoryPort {
    suspend fun create(account: Account): UUID

    suspend fun update(account: Account): Account

    suspend fun getAll(): List<Account>

    suspend fun getById(id: UUID): Account

    suspend fun getByName(name: String): Account
}
