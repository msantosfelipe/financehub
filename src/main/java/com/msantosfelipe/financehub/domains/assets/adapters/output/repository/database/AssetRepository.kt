package com.msantosfelipe.financehub.domains.assets.adapters.output.repository.database

import com.msantosfelipe.financehub.domains.assets.domain.model.Asset
import com.msantosfelipe.financehub.domains.assets.ports.output.AssetRepositoryPort
import com.msantosfelipe.financehub.shared.exceptions.GenericAlreadyExistsException
import com.msantosfelipe.financehub.shared.exceptions.GenericNotFoundException
import io.micronaut.data.exceptions.DataAccessException
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.toList
import java.util.UUID

@Singleton
class AssetRepository(
    val repository: AssetPostgresRepository,
) : AssetRepositoryPort {
    val domainType = "Asset"

    override suspend fun create(asset: Asset): UUID {
        try {
            return repository.save(entity = asset).id
        } catch (e: DataAccessException) {
            if (e.message?.contains("duplicate key value") == true) {
                throw GenericAlreadyExistsException(
                    domainType = domainType,
                    field = "ticker",
                    value = asset.ticker,
                )
            } else {
                throw e
            }
        }
    }

    override suspend fun getAll(): List<Asset> = repository.findAll().toList()

    override suspend fun getByTicker(ticker: String): Asset =
        repository.findByTicker(ticker) ?: throw GenericNotFoundException(
            domainType = domainType,
            field = "ticker",
            value = ticker,
        )
}
