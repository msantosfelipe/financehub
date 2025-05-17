package com.msantosfelipe.financehub.domains.cashflow.ports.input

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentEntry
import java.time.LocalDate
import java.util.UUID

interface InvestmentEntryServicePort {
    suspend fun createInvestmentEntry(investmentEntry: InvestmentEntry): UUID

    suspend fun getInvestmentEntryById(id: UUID): InvestmentEntry

    suspend fun updateInvestmentEntry(investmentEntry: InvestmentEntry): InvestmentEntry

    suspend fun deleteInvestmentEntry(id: UUID)

    suspend fun listInvestmentEntriesByDateRange(
        initDate: LocalDate,
        endDate: LocalDate,
    ): List<InvestmentEntry>
}
