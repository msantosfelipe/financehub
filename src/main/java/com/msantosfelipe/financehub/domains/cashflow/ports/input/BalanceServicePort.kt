package com.msantosfelipe.financehub.domains.cashflow.ports.input

import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowBalanceReport
import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentsBalanceReport
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Year

interface BalanceServicePort {
    suspend fun persistBalanceByIncome(
        referenceDate: LocalDate,
        grossIncomeAmount: BigDecimal,
        netIncomeAmount: BigDecimal,
    )

    suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    )

    suspend fun persistBalanceByInvestment(
        referenceDate: LocalDate,
        investmentAmount: BigDecimal,
    )

    suspend fun listCashFlowByYear(year: Year): List<CashFlowBalanceReport>

    suspend fun getInvestmentsBalanceReport(): InvestmentsBalanceReport
}
