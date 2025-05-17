package com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.CreateExpenseEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.adapters.input.rest.dto.UpdateExpenseEntryDTO
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseCategory
import com.msantosfelipe.financehub.domains.cashflow.domain.model.ExpenseEntry
import com.msantosfelipe.financehub.domains.cashflow.ports.input.ExpenseEntryServicePort
import com.msantosfelipe.financehub.shared.adapters.rest.conversions.Conversions
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import java.time.YearMonth
import java.util.UUID

@Controller("api/v1/expenses")
class ExpenseEntryController(
    val expenseEntryService: ExpenseEntryServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createExpenseEntry(
        @Body expenseEntryRequest: CreateExpenseEntryDTO,
    ): UUID {
        val category = expenseEntryService.getExpenseCategory(expenseEntryRequest.category)
        return expenseEntryService.createExpenseEntry(
            ExpenseEntry(
                referenceDate = YearMonth.parse(expenseEntryRequest.referenceDate).atEndOfMonth(),
                category = category,
                amount = expenseEntryRequest.amount,
                description = expenseEntryRequest.description,
                isFixedExpense = expenseEntryRequest.isFixedExpense,
            ),
        )
    }

    @Put(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun updateExpenseEntry(
        @PathVariable uuid: UUID,
        @Body expenseEntryRequest: UpdateExpenseEntryDTO,
    ): ExpenseEntry {
        val expense = expenseEntryService.getExpenseEntryById(uuid)
        val category = expenseEntryService.getExpenseCategory(expenseEntryRequest.category)
        return expenseEntryService.updateExpenseEntry(
            expenseEntry =
                expense.copy(
                    referenceDate = expense.referenceDate,
                    category = category,
                    amount = expenseEntryRequest.amount,
                    description = expenseEntryRequest.description,
                    isFixedExpense = expenseEntryRequest.isFixedExpense,
                ),
        )
    }

    @Get("categories")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listAllExpenseCategories(): List<ExpenseCategory> = expenseEntryService.getAllExpenseCategories()

    @Delete(value = "/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun deleteExpenseEntry(
        @PathVariable uuid: UUID,
    ) = expenseEntryService.deleteExpenseEntry(uuid)

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listExpenseEntriesByDateRange(
        @QueryValue initDate: String,
        @QueryValue endDate: String?,
    ): List<ExpenseEntry> {
        val (init, end) = Conversions.parseYearMonthDates(initDate, endDate)
        return expenseEntryService.listExpenseEntriesByDateRange(init, end)
    }
}
