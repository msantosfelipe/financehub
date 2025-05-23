package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentGroupByMonth
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentItem
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.input.InvestmentEntryServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.InvestmentEntryRepositoryPort
import jakarta.inject.Singleton
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID
import kotlin.collections.component1
import kotlin.collections.component2

@Singleton
class InvestmentEntryUseCase(
    val repository: InvestmentEntryRepositoryPort,
    val balanceService: BalanceServicePort,
) : InvestmentEntryServicePort {
    override suspend fun createInvestmentEntry(investmentEntry: InvestmentEntry): UUID {
        val createdEntry = repository.createInvestmentEntry(investmentEntry)
        persistBalance(createdEntry.referenceDate)
        return createdEntry.id
    }

    override suspend fun getInvestmentEntryById(id: UUID): InvestmentEntry = repository.getInvestmentEntryById(id)

    override suspend fun updateInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry {
        val updatedEntry = repository.updateInvestmentEntry(investmentEntry)
        persistBalance(updatedEntry.referenceDate)
        return updatedEntry
    }

    override suspend fun deleteInvestmentEntry(id: UUID) {
        val investment = getInvestmentEntryById(id)
        repository.deleteInvestmentEntry(id)
        persistBalance(investment.referenceDate)
    }

    override suspend fun listInvestmentEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentGroupByMonth> {
        val investments = repository.listInvestmentEntriesByDateRange(initDate, endDate)
        return groupByMonthAndType(investments)
    }

    suspend fun persistBalance(referenceDate: LocalDate) {
        val investmentAmount = repository.sumAmountsByReferenceDate(referenceDate)
        balanceService.persistBalanceByInvestment(referenceDate, investmentAmount)
    }

    fun groupByMonthAndType(investments: List<InvestmentEntry>): List<InvestmentGroupByMonth> {
        val formatter = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return investments
            .groupBy { it.referenceDate }
            .map { (date, investmentsByDate) ->
                val investmentsGroupedByType =
                    investmentsByDate
                        .groupBy { it.investment.type }
                        .mapValues { (_, investmentByType) ->
                            investmentByType.map {
                                InvestmentItem(
                                    name = it.investment.name,
                                    description = it.investment.description ?: "",
                                    amount = formatter.format(it.amount),
                                )
                            }
                        }
                InvestmentGroupByMonth(
                    date = date.format(DateTimeFormatter.ofPattern("yyyy/MM")),
                    investments = investmentsGroupedByType,
                )
            }
    }
}
