package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.external.alphavantage.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class AlphaVantageTickerSearchResponse(
    val bestMatches: List<TickerSearchBestMatch>,
)

@Serdeable
data class TickerSearchBestMatch(
    @JsonProperty("1. symbol") val symbol: String,
    @JsonProperty("2. name") val name: String,
    @JsonProperty("3. type") val type: String,
    @JsonProperty("4. region") val region: String,
    @JsonProperty("5. marketOpen") val marketOpen: String,
    @JsonProperty("6. marketClose") val marketClose: String,
    @JsonProperty("7. timezone") val timezone: String,
    @JsonProperty("8. currency") val currency: String,
    @JsonProperty("9. matchScore") val matchScore: String,
)
