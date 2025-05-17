package com.msantosfelipe.financehub.domains.cashflow.ports.input

import com.msantosfelipe.financehub.domains.cashflow.domain.model.Investment
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentType
import java.util.UUID

interface InvestmentServicePort {
    suspend fun createInvestment(investment: Investment): UUID

    suspend fun getInvestmentById(id: UUID): Investment

    suspend fun listActiveInvestments(): List<Investment>

    suspend fun updateInvestment(investment: Investment): Investment

    fun listInvestmentTypes(): List<InvestmentType>
}
