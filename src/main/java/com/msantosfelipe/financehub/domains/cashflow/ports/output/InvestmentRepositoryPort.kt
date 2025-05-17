package com.msantosfelipe.financehub.domains.cashflow.ports.output

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import java.util.UUID

interface InvestmentRepositoryPort {
    suspend fun createInvestment(investment: Investment): UUID

    suspend fun getInvestmentById(id: UUID): Investment

    suspend fun listActiveInvestments(): List<Investment>

    suspend fun updateInvestment(investment: Investment): Investment
}
