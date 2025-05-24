package com.msantosfelipe.financehub.domains.cashflow.domain.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import jakarta.persistence.Column
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale
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

@Serdeable
data class CashFlowReportRaw(
    @MappedEntity("month_number")
    val monthNumber: Int,
    @MappedEntity("total_gross_incomes")
    val totalGrossIncomes: BigDecimal,
    @MappedEntity("total_net_incomes")
    val totalNetIncomes: BigDecimal,
    @MappedEntity("total_fixed_expenses")
    val totalFixedExpenses: BigDecimal,
    @MappedEntity("total_other_expenses")
    val totalOtherExpenses: BigDecimal,
)

@Serdeable
data class CashFlowBalanceReport(
    val monthName: String,
    val totalGrossIncomes: BigDecimal,
    val totalNetIncomes: BigDecimal,
    val totalFixedExpenses: BigDecimal,
    val totalOtherExpenses: BigDecimal,
    val cashAfterFixedExpenses: BigDecimal,
    val cashAfterAllExpenses: BigDecimal,
)

@Serdeable
data class InvestmentReportRaw(
    val referenceDate: String,
    @Column(precision = 15, scale = 2)
    val totalInvested: BigDecimal,
)

@Serdeable
data class InvestmentReport(
    val referenceDate: String,
    val totalInvested: BigDecimal,
    val variancePercent: String,
)

@Serdeable
data class InvestmentsBalanceReport(
    val currentYear: List<InvestmentReport>,
    val pastYears: List<InvestmentReport>,
)
