package com.msantosfelipe.financehub.adapters.output.repository.database

import com.msantosfelipe.financehub.domain.model.Account
import com.msantosfelipe.financehub.ports.output.AccountNotFoundException
import com.msantosfelipe.financehub.ports.output.AccountRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList

@Singleton
class AccountRepository(
    val repository: AccountPostgresRepository
) : AccountRepository {
    override suspend fun create(account: Account) = repository.save(entity = account).id!!

    override suspend fun update(account: Account) = repository.update(entity = account)

    override suspend fun getAll(): List<Account> = repository.findAll().toList()

    override suspend fun getByName(name: String): Account? = repository.findByName(name)?: throw AccountNotFoundException(
        "Account with name $name not found"
    )
}