package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.domain.model.CashFlowReportRaw
import com.msantosfelipe.financehub.domains.cashflow.domain.model.InvestmentsBalanceReport
import com.msantosfelipe.financehub.domains.cashflow.ports.input.BalanceServicePort
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import java.time.Year

@Controller("api/v1/balances")
class BalanceController(
    val balanceService: BalanceServicePort,
) {
    @Get("cashflow")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getCashFlowBalanceReport(
        @QueryValue year: String,
    ): List<CashFlowReportRaw> = balanceService.listCashFlowByYear(Year.parse(year))

    @Get("investments")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getInvestmentsBalanceReport(): InvestmentsBalanceReport = balanceService.getInvestmentsBalanceReport()
}
