package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.MonthlyBalance
import java.time.LocalDate
import java.util.UUID

interface BalanceRepositoryPort {
    suspend fun create(balance: MonthlyBalance): UUID

    suspend fun update(balance: MonthlyBalance): MonthlyBalance

    suspend fun getByReferenceDate(date: LocalDate): MonthlyBalance?

    suspend fun listCashFlowByYear(year: String): List<CashFlowReportRaw>

    suspend fun listTotalInvestedFromCurrentYear(): List<InvestmentReportRaw>

    suspend fun listTotalInvestedFromPastYears(): List<InvestmentReportRaw>
}
