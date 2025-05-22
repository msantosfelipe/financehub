package com.msantosfelipe.financehub.domains.balance.ports.output

import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance
import java.time.LocalDate
import java.util.UUID

interface BalanceRepositoryPort {
    suspend fun create(balance: MonthlyBalance): UUID

    suspend fun update(balance: MonthlyBalance): MonthlyBalance

    suspend fun getByReferenceDate(date: LocalDate): MonthlyBalance?
}
