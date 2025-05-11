package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage

import com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.client.AlphaVantageClient
import com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.dto.TickerSearchBestMatches
import com.msantosfelipe.financehub.shared.exceptions.GenericException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import io.micronaut.context.annotation.Value
import jakarta.inject.Singleton

@Singleton
class AlphaVantageRepository(
    val alphaVantageClient: AlphaVantageClient,
    @Value("\${micronaut.http.services.alpha-vantage.apikey}") private val apiKey: String,
) {
    val domainType = "Asset API"

    suspend fun tickerSearch(ticker: String): TickerSearchBestMatches {
        val response = alphaVantageClient.tickerSearch(ticker = ticker, apiKey = apiKey)

        if (response.bestMatches.isEmpty()) {
            throw GenericNotFoundException(
                domainType = domainType,
                field = "ticker",
                value = ticker,
            )
        }

        val bestMatch =
            response.bestMatches.firstOrNull {
                it.symbol.split(".").first() == ticker
            } ?: throw GenericException("No ticker found with name $ticker in Alpha Vantage API")

        return bestMatch
    }
}
