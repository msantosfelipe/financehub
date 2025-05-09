package com.msantosfelipe.financehub.shared.exceptions.rest

import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.core.convert.exceptions.ConversionErrorException

fun httpConversionErrorHandler(ex: ConversionErrorException): ErrorDto {
    val message = ex.cause?.message ?: ex.message ?: "Unknown conversion error"
    return ErrorDto("ConversionError. $message")
}
