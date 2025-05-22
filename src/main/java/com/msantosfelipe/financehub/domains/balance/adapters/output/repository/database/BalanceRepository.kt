package com.msantosfelipe.financehub.domains.balance.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.balance.domain.model.MonthlyBalance
import com.msantosfelipe.financehub.domains.balance.ports.output.BalanceRepositoryPort
import jakarta.inject.Singleton
import java.time.LocalDate
import java.util.UUID

@Singleton
class BalanceRepository(
    val repository: BalancePostgresRepository,
) : BalanceRepositoryPort {
    override suspend fun create(balance: MonthlyBalance): UUID = repository.save(balance).id

    override suspend fun update(balance: MonthlyBalance): MonthlyBalance = repository.update(balance)

    override suspend fun getByReferenceDate(date: LocalDate): MonthlyBalance? = repository.findByReferenceDate(date)
}
