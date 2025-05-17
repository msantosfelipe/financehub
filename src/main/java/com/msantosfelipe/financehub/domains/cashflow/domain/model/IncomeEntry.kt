package com.msantosfelipe.financehub.domains.cashflow.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@MappedEntity(value = "incomes_entry")
@Serdeable
data class IncomeEntry(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val referenceDate: LocalDate,
    @Column(precision = 15, scale = 2)
    val grossAmount: BigDecimal,
    @Column(precision = 15, scale = 2)
    val discountAmount: BigDecimal,
    @Column(precision = 15, scale = 2)
    val netAmount: BigDecimal,
    val type: IncomeType,
    val description: String?,
)

enum class IncomeType {
    SALARY,
    TAX_REFUND,
    YEAR_END_BONUS,
    PROFIT_SHARING,
    UNKNOWN,
}

internal val incomeTypeLabelsPTBR =
    mapOf(
        IncomeType.SALARY to "Salário",
        IncomeType.YEAR_END_BONUS to "13ª Salário",
        IncomeType.PROFIT_SHARING to "PLR",
        IncomeType.TAX_REFUND to "Restituição IR",
        IncomeType.UNKNOWN to "Não Definido",
    )
