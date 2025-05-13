package com.msantosfelipe.financehub.domains.accounts.domain.usecase

import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import com.msantosfelipe.financehub.domains.accounts.domain.model.AccountType
import com.msantosfelipe.financehub.domains.accounts.domain.model.normalizeAccountName
import com.msantosfelipe.financehub.domains.accounts.ports.input.AccountServicePort
import com.msantosfelipe.financehub.domains.accounts.ports.output.AccountRepositoryPort
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

    override fun listAccountTypes(): List<AccountType> = AccountType.entries
}
