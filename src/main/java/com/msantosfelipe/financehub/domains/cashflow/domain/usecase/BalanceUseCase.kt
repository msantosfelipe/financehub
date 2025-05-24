package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentReport
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentsBalanceReport
import com.msantosfelipe.financehub.domains.cashflow.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.BalanceRepositoryPort
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Singleton
class BalanceUseCase(
    val balanceRepository: BalanceRepositoryPort,
) : BalanceServicePort {
    override suspend fun persistBalanceByIncome(
        referenceDate: LocalDate,
        grossIncomeAmount: BigDecimal,
        netIncomeAmount: BigDecimal,
    ) {
        val existingBalance = balanceRepository.getByReferenceDate(referenceDate)

        val updatedBalance =
            existingBalance?.copy(
                totalGrossIncomes = grossIncomeAmount,
                totalNetIncomes = netIncomeAmount,
            )
                ?: MonthlyBalance(
                    referenceDate = referenceDate,
                    totalGrossIncomes = grossIncomeAmount,
                    totalNetIncomes = netIncomeAmount,
                )

        if (existingBalance == null) {
            balanceRepository.create(updatedBalance)
        } else {
            balanceRepository.update(updatedBalance)
        }
    }

    override suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    ) {
        val existingBalance = balanceRepository.getByReferenceDate(referenceDate)

        val updatedBalance =
            existingBalance?.copy(
                totalFixedExpenses = fixedExpenseAmount,
                totalOtherExpenses = variableExpenseAmount,
            )
                ?: MonthlyBalance(
                    referenceDate = referenceDate,
                    totalFixedExpenses = fixedExpenseAmount,
                    totalOtherExpenses = variableExpenseAmount,
                )

        if (existingBalance == null) {
            balanceRepository.create(updatedBalance)
        } else {
            balanceRepository.update(updatedBalance)
        }
    }

    override suspend fun persistBalanceByInvestment(
        referenceDate: LocalDate,
        investmentAmount: BigDecimal,
    ) {
        val existingBalance = balanceRepository.getByReferenceDate(referenceDate)

        val updatedBalance =
            existingBalance?.copy(totalInvested = investmentAmount)
                ?: MonthlyBalance(
                    referenceDate = referenceDate,
                    totalInvested = investmentAmount,
                )

        if (existingBalance == null) {
            balanceRepository.create(updatedBalance)
        } else {
            balanceRepository.update(updatedBalance)
        }
    }

    override suspend fun getInvestmentsBalanceReport(): InvestmentsBalanceReport {
        val currentYearInvestments =
            calculateWithVariance(
                rawData = balanceRepository.listTotalInvestedFromCurrentYear(),
                parse = { YearMonth.parse(it.referenceDate, DateTimeFormatter.ofPattern("yyyy-MM")) },
            )

        val pastYearsInvestments =
            calculateWithVariance(
                rawData = balanceRepository.listTotalInvestedFromPastYears(),
                parse = { Year.parse(it.referenceDate) },
            )

        return InvestmentsBalanceReport(
            currentYear = currentYearInvestments,
            pastYears = pastYearsInvestments,
        )
    }

    private fun <T : Comparable<T>> calculateWithVariance(
        rawData: List<InvestmentReportRaw>,
        parse: (InvestmentReportRaw) -> T,
    ): List<InvestmentReport> {
        val sorted =
            rawData
                .map { parse(it) to it.totalInvested }
                .sortedBy { it.first }

        return sorted.mapIndexed { index, (period, value) ->
            val previous = sorted.getOrNull(index - 1)?.second
            val variance =
                previous?.takeIf { it != BigDecimal.ZERO }?.let {
                    ((value - it) / it * BigDecimal(100)).setScale(2, RoundingMode.HALF_UP).toString() + "%"
                } ?: "N/A"

            InvestmentReport(
                referenceDate = period.toString(),
                totalInvested = value,
                variancePercent = variance,
            )
        }
    }
}
