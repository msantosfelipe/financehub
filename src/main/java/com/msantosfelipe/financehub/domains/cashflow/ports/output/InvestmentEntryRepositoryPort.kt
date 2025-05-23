package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

interface InvestmentEntryRepositoryPort {
    suspend fun createInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry

    suspend fun getInvestmentEntryById(id: UUID): InvestmentEntry

    suspend fun updateInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry

    suspend fun deleteInvestmentEntry(id: UUID)

    suspend fun listInvestmentEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentEntry>

    suspend fun sumAmountsByReferenceDate(referenceDate: LocalDate): BigDecimal
}
