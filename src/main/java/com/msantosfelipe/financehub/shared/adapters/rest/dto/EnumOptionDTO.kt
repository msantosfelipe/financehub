package com.msantosfelipe.financehub.shared.adapters.rest.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class EnumOptionDTO(
    val key: String,
    val label: String,
)
