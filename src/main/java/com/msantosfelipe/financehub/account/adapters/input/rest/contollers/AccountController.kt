package com.msantosfelipe.financehub.account.adapters.input.rest.contollers

import com.msantosfelipe.financehub.account.adapters.input.rest.dto.CreateAccountRequest
import com.msantosfelipe.financehub.account.adapters.input.rest.dto.UpdateAccountRequest
import com.msantosfelipe.financehub.account.domain.model.Account
import com.msantosfelipe.financehub.account.domain.model.AccountType
import com.msantosfelipe.financehub.account.ports.input.AccountServicePort
import com.msantosfelipe.financehub.commons.adapters.input.rest.dto.ErrorDto
import com.msantosfelipe.financehub.commons.adapters.input.rest.dto.httpConversionErrorHandler
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
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
            account =
                account.copy(
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

    @Error(global = true)
    fun handleConversionError(
        request: HttpRequest<*>,
        ex: ConversionErrorException,
    ): HttpResponse<ErrorDto> {
        return HttpResponse.badRequest(
            httpConversionErrorHandler(ex),
        )
    }
}
