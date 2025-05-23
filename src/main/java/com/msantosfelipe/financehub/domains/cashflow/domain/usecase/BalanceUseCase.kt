package com.msantosfelipe.financehub.domains.cashflow.domain.usecase

import com.msantosfelipe.financehub.domains.cashflow.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import com.msantosfelipe.financehub.domains.cashflow.ports.output.BalanceRepositoryPort
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.time.LocalDate

@Singleton
class BalanceUseCase(
    val balanceRepository: BalanceRepositoryPort,
) : BalanceServicePort {
    override suspend fun persistBalanceByExpense(
        referenceDate: LocalDate,
        fixedExpenseAmount: BigDecimal,
        variableExpenseAmount: BigDecimal,
    ) {
        val existingBalance = balanceRepository.getByReferenceDate(referenceDate)

        val updatedBalance =
            existingBalance?.copy(
                totalFixedExpenses = fixedExpenseAmount,
                totalOtherExpenses = variableExpenseAmount,
            )
                ?: MonthlyBalance(
                    referenceDate = referenceDate,
                    totalFixedExpenses = fixedExpenseAmount,
                    totalOtherExpenses = variableExpenseAmount,
                )

        if (existingBalance == null) {
            balanceRepository.create(updatedBalance)
        } else {
            balanceRepository.update(updatedBalance)
        }
    }
}
