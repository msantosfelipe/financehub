package com.msantosfelipe.financehub.domain.accounts.service

import com.msantosfelipe.financehub.adapters.accounts.out.persistence.AccountRepository
import com.msantosfelipe.financehub.domain.accounts.model.Account
import java.util.UUID

class AccountService(
    private val accountRepository: AccountRepository
) {
    fun createAccount(account: Account): UUID {
        // TODO
        return accountRepository.create(account)
    }

    fun updateAccount(account: Account): Account {
        // TODO
        return accountRepository.update(account)
    }

    fun getAccountById(name: String): Account? {
        // TODO
        return accountRepository.getByName(name)
    }

    fun getAllAccounts(): List<Account> {
        // TODO
        return accountRepository.getAll()
    }
}