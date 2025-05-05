package com.msantosfelipe.financehub.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ErrorDto(
    val message: String? = "",
)
