package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentType
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CreateInvestmentDTO(
    val name: String,
    val description: String?,
    val type: InvestmentType,
)

@Serdeable
data class UpdateInvestmentDTO(
    val name: String,
    val description: String?,
    val type: InvestmentType,
    val active: Boolean,
)
