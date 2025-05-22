package com.msantosfelipe.financehub.domains.balance.domain.usecase

import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.balance.ports.input.BalanceServicePort
import com.msantosfelipe.financehub.domains.balance.ports.output.BalanceRepositoryPort
import jakarta.inject.Singleton

@Singleton
class BalanceUseCase(
    val balanceRepository: BalanceRepositoryPort,
) : BalanceServicePort {
    override suspend fun createOrUpdateBalance(balance: MonthlyBalance) {
        val existingBalance = balanceRepository.getByReferenceDate(balance.referenceDate)
        if (existingBalance == null) {
            balanceRepository.create(balance)
        } else {
            balanceRepository.update(
                balance =
                    balance.copy(
                        totalGrossIncomes = balance.totalGrossIncomes,
                        totalNetIncomes = balance.totalNetIncomes,
                        totalFixedExpenses = balance.totalFixedExpenses,
                        totalOtherExpenses = balance.totalOtherExpenses,
                        totalInvested = balance.totalInvested,
                    ),
            )
        }
    }
}
