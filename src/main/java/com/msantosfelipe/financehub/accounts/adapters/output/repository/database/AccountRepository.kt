package com.msantosfelipe.financehub.accounts.adapters.output.repository.database

import com.msantosfelipe.financehub.accounts.domain.model.Account
import com.msantosfelipe.financehub.accounts.ports.output.AccountAlreadyExistsException
import com.msantosfelipe.financehub.accounts.ports.output.AccountNotFoundException
import com.msantosfelipe.financehub.accounts.ports.output.AccountRepositoryPort
import io.micronaut.data.exceptions.DataAccessException
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.util.UUID

@Singleton
class AccountRepository(
    val repository: AccountPostgresRepository,
) : AccountRepositoryPort {
    override suspend fun create(account: Account): UUID {
        try {
            return repository.save(entity = account).id
        } catch (e: DataAccessException) {
            if (e.message?.contains("duplicate key value") == true) {
                throw AccountAlreadyExistsException("Account with name '${account.name}' already exists.")
            } else {
                throw e
            }
        }
    }

    override suspend fun update(account: Account) = repository.update(entity = account)

    override suspend fun getAll(): List<Account> = repository.findAll().toList()

    override suspend fun getById(id: UUID): Account =
        repository.findById(id) ?: throw AccountNotFoundException(
            "Account with name $id not found",
        )

    override suspend fun getByName(name: String): Account =
        repository.findByName(name) ?: throw AccountNotFoundException(
            "Account with name $name not found",
        )
}
