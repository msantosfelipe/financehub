package com.msantosfelipe.financehub.domains.assets.adapters.input.rest.controller

import com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto.AssetEarningRequest
import com.msantosfelipe.financehub.domains.assets.adapters.input.rest.dto.CreateAssetRequest
import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import com.msantosfelipe.financehub.domains.assets.domain.model.MonthlyAssetEarning
import com.msantosfelipe.financehub.domains.assets.ports.input.AssetServicePort
import com.msantosfelipe.financehub.domains.assets.ports.input.MonthlyAssetEarningServicePort
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import com.msantosfelipe.financehub.shared.exceptions.rest.dto.ErrorDto
import io.micronaut.core.convert.exceptions.ConversionErrorException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import java.time.YearMonth
import java.util.UUID

@Controller("api/v1/assets")
class AssetController(
    val assetService: AssetServicePort,
    val monthlyAssetEarningService: MonthlyAssetEarningServicePort,
) {
    @Post
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createAsset(
        @Body createAssetRequest: CreateAssetRequest,
    ): UUID = assetService.createAsset(createAssetRequest.ticker)

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAllAssets(): List<Asset> = assetService.getAllAssets()

    @Get(value = "/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun getAssetByTicker(
        @PathVariable ticker: String,
    ): Asset = assetService.getAssetByTicker(ticker)

    @Post("/earnings")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun createOrUpdateEarning(
        @Body assetEarningRequest: AssetEarningRequest,
    ): UUID {
        val asset = assetService.getAssetByTicker(assetEarningRequest.ticker)
        return monthlyAssetEarningService.createOrUpdateEarningEntry(
            MonthlyAssetEarning(
                assetId = asset.id,
                amountReceived = assetEarningRequest.amountReceived,
                referenceDate = YearMonth.parse(assetEarningRequest.referenceDate).atEndOfMonth(),
                notes = assetEarningRequest.notes,
            ),
        )
    }

    @Get("/earnings/ticker/{ticker}")
    @Produces(MediaType.APPLICATION_JSON)
    suspend fun listEarningsByAsset(
        @PathVariable ticker: String,
    ): List<MonthlyAssetEarning> {
        val asset = assetService.getAssetByTicker(ticker)
        return monthlyAssetEarningService.listEarningsByAsset(asset.id)
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
