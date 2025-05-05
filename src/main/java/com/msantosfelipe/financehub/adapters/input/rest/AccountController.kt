package com.msantosfelipe.financehub.adapters.input.rest

import com.msantosfelipe.financehub.adapters.input.rest.dto.CreateAccountRequest
import com.msantosfelipe.financehub.adapters.input.rest.dto.UpdateAccountRequest
import com.msantosfelipe.financehub.domain.model.Account
import com.msantosfelipe.financehub.domain.model.AccountType
import com.msantosfelipe.financehub.ports.input.AccountServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import java.util.UUID

@Controller("api/v1/accounts")
class AccountController(
    val accountService: AccountServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createAccount(
        @Body createAccountRequest: CreateAccountRequest,
    ): UUID =
        accountService.createAccount(
            Account(
                displayName = createAccountRequest.displayName,
                bankCode = createAccountRequest.bankCode,
                agency = createAccountRequest.agency,
                accountNumber = createAccountRequest.accountNumber,
                pix = createAccountRequest.pix,
                loginUser = createAccountRequest.loginUser,
                accountType = createAccountRequest.accountType ?: AccountType.NOT_DEFINED,
                description = createAccountRequest.useDescription,
                active = createAccountRequest.active ?: true,
            ),
        )

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateAccount(
        @PathVariable uuid: UUID,
        @Body updateAccountRequest: UpdateAccountRequest,
    ): Account {
        val account = accountService.getAccountById(uuid)
        return accountService.updateAccount(
            Account(
                id = account.id,
                displayName = account.displayName,
                bankCode = updateAccountRequest.bankCode,
                agency = updateAccountRequest.agency,
                accountNumber = updateAccountRequest.accountNumber,
                pix = updateAccountRequest.pix,
                loginUser = updateAccountRequest.loginUser,
                accountType = updateAccountRequest.accountType,
                description = updateAccountRequest.useDescription,
                active = updateAccountRequest.active,
            ),
        )
    }

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAllAccounts(): List<Account> = accountService.getAllAccounts()

    @Get(value = "/{searchName}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAccountByName(
        @PathVariable searchName: String,
    ): Account = accountService.getAccountByName(searchName)
}
