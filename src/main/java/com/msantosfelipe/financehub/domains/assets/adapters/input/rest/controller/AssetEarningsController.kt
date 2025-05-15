package com.msantosfelipe.financehub.domains.assets.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto.AssetEarningRequest
import com.msantosfelipe.financehub.domains.assets.domain.model.AssetEarning
import com.msantosfelipe.financehub.domains.assets.domain.model.EarningGroupByMonth
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetEarningServicePort
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetServicePort
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.exceptions.HttpStatusException
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeParseException
import java.util.UUID

@Controller("api/v1/assets/earnings")
class AssetEarningsController(
    val assetService: AssetServicePort,
    val assetEarningService: AssetEarningServicePort,
) {
    @Post()
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createOrUpdateEarning(
        @Body assetEarningRequest: AssetEarningRequest,
    ): UUID {
        val asset = assetService.getAssetByTicker(assetEarningRequest.ticker)
        return assetEarningService.createOrUpdateEarningEntry(
            AssetEarning(
                assetId = asset.id,
                totalAmountReceived = assetEarningRequest.amountReceived,
                referenceDate = YearMonth.parse(assetEarningRequest.referenceDate).atEndOfMonth(),
                notes = assetEarningRequest.notes,
            ),
        )
    }

    @Get("/ticker/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listEarningsByAsset(
        @PathVariable ticker: String,
    ): List<AssetEarning> {
        val asset = assetService.getAssetByTicker(ticker)
        return assetEarningService.listEarningsByAsset(asset.id)
    }

    @Get()
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listEarningsByDateRange(
        @QueryValue initDate: String,
        @QueryValue endDate: String?,
    ): List<EarningGroupByMonth> {
        fun parseDates(
            initDate: String,
            endDate: String?,
        ): Pair<LocalDate, LocalDate> {
            try {
                val init = YearMonth.parse(initDate).atEndOfMonth()
                val end = endDate?.let { YearMonth.parse(it).atEndOfMonth() } ?: YearMonth.now().atEndOfMonth()
                return Pair(init, end)
            } catch (e: DateTimeParseException) {
                throw HttpStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.message,
                )
            }
        }

        val (init, end) = parseDates(initDate, endDate)
        if (end.isBefore(init)) {
            throw HttpStatusException(
                HttpStatus.BAD_REQUEST,
                "End date is before init date",
            )
        }

        return assetEarningService.listEarningsByDateRange(init, end)
    }

    @Error(global = true)
    fun handleConversionError(
        request: HttpRequest<*>,
        ex: ConversionErrorException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Unknown conversion error"
        return HttpResponse.badRequest(
            ErrorDto("ConversionError. $message"),
        )
    }

    @Error()
    fun handleAlreadyExistsException(
        request: HttpRequest<*>,
        ex: GenericAlreadyExistsException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Already exists error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }

    @Error()
    fun handleNotFoundException(
        request: HttpRequest<*>,
        ex: GenericNotFoundException,
    ): HttpResponse<ErrorDto> {
        val message = ex.cause?.message ?: ex.message ?: "Not found error"
        return HttpResponse.badRequest(
            ErrorDto(message),
        )
    }
}
