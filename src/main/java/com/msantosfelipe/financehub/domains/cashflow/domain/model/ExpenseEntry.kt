package com.msantosfelipe.financehub.domains.cashflow.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@MappedEntity("expense_category")
@Serdeable
data class ExpenseCategory(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val name: String,
)

@MappedEntity(value = "expenses_entry")
@Serdeable
data class ExpenseEntry(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    @Column(precision = 15, scale = 2)
    val referenceDate: LocalDate,
    @Relation(Relation.Kind.MANY_TO_ONE)
    val category: ExpenseCategory,
    val amount: BigDecimal,
    val description: String?,
    val isFixedExpense: Boolean = false,
)
