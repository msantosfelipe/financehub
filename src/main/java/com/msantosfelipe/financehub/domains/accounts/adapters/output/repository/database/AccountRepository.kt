package com.msantosfelipe.financehub.domains.accounts.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import com.msantosfelipe.financehub.domains.accounts.ports.output.AccountRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import io.micronaut.data.exceptions.DataAccessException
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.util.UUID

@Singleton
class AccountRepository(
    val repository: AccountPostgresRepository,
) : AccountRepositoryPort {
    val domainType = "Account"

    override suspend fun create(account: Account): UUID {
        try {
            return repository.save(entity = account).id
        } catch (e: DataAccessException) {
            if (e.message?.contains("duplicate key value") == true) {
                throw GenericAlreadyExistsException(
                    domainType = domainType,
                    field = "name",
                    value = account.name,
                )
            } else {
                throw e
            }
        }
    }

    override suspend fun update(account: Account) = repository.update(entity = account)

    override suspend fun getAll(): List<Account> = repository.findAll().toList()

    override suspend fun getById(id: UUID): Account =
        repository.findById(id) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "id",
            value = id.toString(),
        )

    override suspend fun getByName(name: String): Account =
        repository.findByName(name) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "name",
            value = name,
        )
}
