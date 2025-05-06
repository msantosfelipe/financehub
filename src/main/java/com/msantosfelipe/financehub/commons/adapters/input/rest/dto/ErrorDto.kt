package com.msantosfelipe.financehub.commons.adapters.input.rest.dto

import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorDto(
    val message: String? = "",
)

fun httpConversionErrorHandler(ex: ConversionErrorException): ErrorDto {
    val message = ex.cause?.message ?: ex.message ?: "Unknown conversion error"
    return ErrorDto("ConversionError. $message")
}
