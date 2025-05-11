package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.client

import com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.dto.AlphaVantageTickerSearchResponse
import io.micronaut.http.HttpHeaders
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${micronaut.http.services.alpha-vantage.url}")
interface AlphaVantageClient {
    @Get("/query?function=SYMBOL_SEARCH&keywords={ticker}&apikey={apiKey}")
    @Header(name = HttpHeaders.CONTENT_TYPE, value = MediaType.APPLICATION_JSON)
    suspend fun tickerSearch(
        @QueryValue ticker: String,
        @QueryValue apiKey: String,
    ): AlphaVantageTickerSearchResponse
}
