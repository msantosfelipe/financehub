package com.msantosfelipe.financehub.domains.cashflow.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.cashflow.ports.output.BalanceRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class BalanceRepository(
    val repository: BalancePostgresRepository,
) : BalanceRepositoryPort {
    override suspend fun create(balance: MonthlyBalance): UUID = repository.save(balance).id

    override suspend fun update(balance: MonthlyBalance): MonthlyBalance = repository.update(balance)

    override suspend fun getByReferenceDate(date: LocalDate): MonthlyBalance? = repository.findByReferenceDate(date)

    override suspend fun listCashFlowByYear(year: String): List<CashFlowReportRaw> = repository.listCashFlowByYear(year)

    override suspend fun listTotalInvestedFromCurrentYear(): List<InvestmentReportRaw> = repository.listInvestedFromCurrentYear()

    override suspend fun listTotalInvestedFromPastYears(): List<InvestmentReportRaw> = repository.listTotalInvestedFromPastYears()
}
