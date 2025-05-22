package com.msantosfelipe.financehub.domains.balance.adapters.input.internal

import com.msantosfelipe.financehub.domains.balance.adapters.input.internal.dto.PersistBalanceDto
import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.balance.ports.input.BalanceServicePort
import jakarta.inject.Singleton

@Singleton
class BalanceInternalClient(
    val service: BalanceServicePort,
) {
    suspend fun persistBalance(balance: PersistBalanceDto) =
        service.createOrUpdateBalance(
            balance =
                MonthlyBalance(
                    referenceDate = balance.referenceDate,
                    totalGrossIncomes = balance.totalGrossIncomes,
                    totalNetIncomes = balance.totalNetIncomes,
                    totalFixedExpenses = balance.totalFixedExpenses,
                    totalOtherExpenses = balance.totalOtherExpenses,
                    totalInvested = balance.totalInvested,
                ),
        )
}
