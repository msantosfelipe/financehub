package com.msantosfelipe.financehub.domains.accounts.adapters.input.rest.exception

import com.msantosfelipe.financehub.domains.accounts.ports.output.AccountAlreadyExistsException
import com.msantosfelipe.financehub.domains.accounts.ports.output.AccountNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
class AccountNotFoundExceptionHandler : ExceptionHandler<AccountNotFoundException, HttpResponse<*>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: AccountNotFoundException,
    ): HttpResponse<*> {
        return HttpResponse.notFound(ErrorDto(message = exception.message))
    }
}

@Produces
@Singleton
class AccountAlreadyExistsExceptionHandler : ExceptionHandler<AccountAlreadyExistsException, HttpResponse<*>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: AccountAlreadyExistsException,
    ): HttpResponse<*> {
        return HttpResponse.badRequest(ErrorDto(message = exception.message))
    }
}
