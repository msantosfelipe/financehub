package com.msantosfelipe.financehub.domains.cashflow.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

private const val EXPENSE_CATEGORY_OTHER_LABEL_PTBR = "Outra"

@MappedEntity(value = "expenses_entry")
@Serdeable
data class ExpenseEntry(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    @Column(precision = 15, scale = 2)
    val referenceDate: LocalDate,
    val category: String = EXPENSE_CATEGORY_OTHER_LABEL_PTBR,
    val amount: BigDecimal,
    val description: String?,
    val isFixedExpense: Boolean = false,
)
