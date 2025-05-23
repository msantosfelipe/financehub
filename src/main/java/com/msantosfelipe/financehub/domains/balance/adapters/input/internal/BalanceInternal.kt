package com.msantosfelipe.financehub.domains.balance.adapters.input.internal

import com.msantosfelipe.financehub.domains.balance.adapters.input.internal.dto.PersistBalanceByExpenseDto
import com.msantosfelipe.financehub.domains.balance.ports.input.BalanceServicePort
import jakarta.inject.Singleton

@Singleton
class BalanceInternal(
    val service: BalanceServicePort,
) {
    suspend fun persistBalanceByExpense(expenseData: PersistBalanceByExpenseDto) =
        service.persistBalanceByExpense(
            expenseData.referenceDate,
            expenseData.fixedExpenseAmount,
            expenseData.variableExpenseAmount,
        )
}
