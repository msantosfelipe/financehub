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
    val totalGrossIncomes: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalNetIncomes: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalFixedExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalOtherExpenses: BigDecimal,
    @Column(precision = 15, scale = 2)
    val totalInvested: BigDecimal,
)
