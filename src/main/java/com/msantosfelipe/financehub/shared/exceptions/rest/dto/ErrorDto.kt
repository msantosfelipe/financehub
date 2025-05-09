package com.msantosfelipe.financehub.shared.exceptions.rest.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorDto(
    val message: String? = "",
)
