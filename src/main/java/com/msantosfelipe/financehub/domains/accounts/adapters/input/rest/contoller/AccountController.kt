package com.msantosfelipe.financehub.domains.accounts.adapters.input.rest.contoller

import com.msantosfelipe.financehub.domains.accounts.adapters.input.rest.dto.CreateAccountRequest
import com.msantosfelipe.financehub.domains.accounts.adapters.input.rest.dto.UpdateAccountRequest
import com.msantosfelipe.financehub.domains.accounts.domain.model.Account
import com.msantosfelipe.financehub.domains.accounts.domain.model.AccountType
import com.msantosfelipe.financehub.domains.accounts.domain.model.accountTypeLabels
import com.msantosfelipe.financehub.domains.accounts.ports.input.AccountServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.dto.EnumOptionDTO
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
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
                description = createAccountRequest.description,
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
                    description = updateAccountRequest.description,
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

    @Get("types")
    @Produces(MediaType.APPLICATION_JSON)
    fun listAccountTypes(): List<EnumOptionDTO> {
        val accountTypes = accountService.listAccountTypes()
        return accountTypes.map {
            EnumOptionDTO(
                key = it.name,
                label = accountTypeLabels[it] ?: it.name,
            )
        }
    }

    @Error(global = true)
    fun handleConversionError(
        request: HttpRequest<*>,
        ex: ConversionErrorException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Unknown conversion error"
        return HttpResponse.badRequest(
            ErrorDto("ConversionError. $message"),
        )
    }

    @Error()
    fun handleAlreadyExistsException(
        request: HttpRequest<*>,
        ex: GenericAlreadyExistsException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Already exists error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }

    @Error()
    fun handleNotFoundException(
        request: HttpRequest<*>,
        ex: GenericNotFoundException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Not found error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }
}
