package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowBalanceReport
import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowReportRaw
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
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

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

    override suspend fun listCashFlowByYear(year: Year): List<CashFlowBalanceReport> {
        val rawReport = balanceRepository.listCashFlowByYear(year.toString())
        val report = rawReport.map {
            CashFlowBalanceReport(
                monthName = Month.of(it.monthNumber)
                    .getDisplayName(TextStyle.FULL, Locale("pt", "BR")),
                totalGrossIncomes = it.totalGrossIncomes,
                totalNetIncomes = it.totalNetIncomes,
                totalFixedExpenses = it.totalFixedExpenses,
                totalOtherExpenses = it.totalOtherExpenses,
                cashAfterFixedExpenses = it.totalNetIncomes - it.totalFixedExpenses,
                cashAfterAllExpenses = it.totalNetIncomes - it.totalFixedExpenses - it.totalOtherExpenses
            )
        }
        return report
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
            val variance = if (previous != null && previous.compareTo(BigDecimal.ZERO) != 0) {
                val diff = (value - previous)
                val percentage = diff.divide(previous, 4, RoundingMode.HALF_UP) * BigDecimal(100)
                "${percentage.setScale(2, RoundingMode.HALF_UP)}%"
            } else {
                "N/A"
            }

            InvestmentReport(
                referenceDate = period.toString(),
                totalInvested = value,
                variancePercent = variance,
            )
        }
    }
}
