package com.msantosfelipe.financehub.domains.balance.ports.input

import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance

interface BalanceServicePort {
    suspend fun createOrUpdateBalance(balance: MonthlyBalance)
}
