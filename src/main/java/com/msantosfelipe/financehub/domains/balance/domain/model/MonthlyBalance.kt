package com.msantosfelipe.financehub.domains.balance.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@MappedEntity(value = "balances")
@Serdeable
data class MonthlyBalance(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val referenceDate: LocalDate,
    @Column(precision = 15, scale = 2)
    val totalGrossIncomes: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 15, scale = 2)
    val totalNetIncomes: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 15, scale = 2)
    val totalFixedExpenses: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 15, scale = 2)
    val totalOtherExpenses: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 15, scale = 2)
    val totalInvested: BigDecimal = BigDecimal.ZERO,
)
