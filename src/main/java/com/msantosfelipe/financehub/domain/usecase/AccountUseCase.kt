package com.msantosfelipe.financehub.domain.usecase

import com.msantosfelipe.financehub.ports.output.AccountRepository
import com.msantosfelipe.financehub.domain.model.Account
import com.msantosfelipe.financehub.ports.input.AccountServicePort
import jakarta.inject.Singleton
import java.util.UUID

@Singleton
class AccountUseCase(
    private val accountRepository: AccountRepository
): AccountServicePort {
    override suspend fun createAccount(account: Account): UUID {
        // TODO
        return accountRepository.create(account)
    }

    override suspend fun updateAccount(account: Account): Account {
        // TODO
        return accountRepository.update(account)
    }

    override suspend fun getAllAccounts(): List<Account> = accountRepository.getAll()

    override suspend fun getAccountByName(name: String): Account? {
        // TODO
        return accountRepository.getByName(name)
    }
}