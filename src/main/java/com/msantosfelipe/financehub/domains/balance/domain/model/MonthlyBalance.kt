package com.msantosfelipe.financehub.domains.balance.domain.model

import io.micronaut.data.annotation.Id
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class MonthlyBalance(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val referenceDate: LocalDate,
    @Column(precision = 15, scale = 2)
    val totalGrossIncomes: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalNetIncomes: BigDecimal,
    @Column(precision = 15, scale = 2)
    val fixExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val otherExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val cashAfterFixedExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val cashAfterTotalExpenses: BigDecimal
)
