package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentsBalanceReport
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces

@Controller("api/v1/balances")
class BalanceController(
    val balanceService: BalanceServicePort,
) {
// balanco (filtro é o ano)
// balance: {
//    referenceDate (YYYY/MM)
//    totalGrossIncomes
//    totalNetIncomes
//    totalFixedExpenses
//    totalOtherExpenses
//    totalInvested
// },

// reserva de emergencia

    @Get("investments")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getInvestmentsBalanceReport(): InvestmentsBalanceReport = balanceService.getInvestmentsBalanceReport()
}
