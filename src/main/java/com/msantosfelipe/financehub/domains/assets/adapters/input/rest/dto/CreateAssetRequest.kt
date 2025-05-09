package com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CreateAssetRequest(
    val ticker: String,
)
