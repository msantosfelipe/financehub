package com.msantosfelipe.financehub.account.domain.usecase

import com.msantosfelipe.financehub.account.domain.model.Account
import com.msantosfelipe.financehub.account.domain.model.normalizeAccountName
import com.msantosfelipe.financehub.account.ports.input.AccountServicePort
import com.msantosfelipe.financehub.account.ports.output.AccountRepositoryPort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class AccountUseCase(
    private val accountRepository: AccountRepositoryPort,
) : AccountServicePort {
    override suspend fun createAccount(account: Account): UUID = accountRepository.create(account)

    override suspend fun updateAccount(account: Account): Account = accountRepository.update(account)

    override suspend fun getAllAccounts(): List<Account> = accountRepository.getAll()

    override suspend fun getAccountById(id: UUID): Account = accountRepository.getById(id)

    override suspend fun getAccountByName(searchName: String): Account =
        accountRepository.getByName(
            name = normalizeAccountName(searchName),
        )
}
