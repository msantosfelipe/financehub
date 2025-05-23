package com.msantosfelipe.financehub.domains.cashflow.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@MappedEntity(value = "investments_entry")
@Serdeable
data class InvestmentEntry(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val referenceDate: LocalDate,
    @Relation(Relation.Kind.MANY_TO_ONE)
    val investment: Investment,
    val amount: BigDecimal,
)

@MappedEntity(value = "investments")
@Serdeable
data class Investment(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String?,
    val type: InvestmentType,
    val active: Boolean = true,
)

enum class InvestmentType {
    FIXED_INCOME,
    VARIABLE_INCOME,
    EMERGENCY_FUND,
    UNKNOWN,
}

internal val investmentTypeLabelsPTBR =
    mapOf(
        InvestmentType.FIXED_INCOME to "Renda fixa",
        InvestmentType.VARIABLE_INCOME to "Renda variável",
        InvestmentType.EMERGENCY_FUND to "Reserva de emergência",
        InvestmentType.UNKNOWN to "Não Definido",
    )

@Serdeable
data class InvestmentGroupByMonth(
    val date: String,
    val investments: Map<InvestmentType, List<InvestmentItem>>,
)

@Serdeable
data class InvestmentItem(
    val name: String,
    val description: String,
    val amount: String,
)
