package com.msantosfelipe.financehub.adapters.input.rest

import com.msantosfelipe.financehub.domain.model.Account
import com.msantosfelipe.financehub.ports.input.AccountServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces

@Controller("api/v1/accounts")
class AccountController(
    val accountService: AccountServicePort
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun create(@Body account: Account) {
        accountService.createAccount(account)
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAllAccounts(): List<Account> = accountService.getAllAccounts()

}