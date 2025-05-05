package com.msantosfelipe.financehub.domain.model

import com.msantosfelipe.financehub.commons.removeAccents
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@MappedEntity(value = "accounts")
@Serdeable
data class Account(
    @field:Id
    val id: UUID = UUID.randomUUID(),
    val displayName: String,
    val name: String = normalizeAccountName(displayName),
    val bankCode: String?,
    val agency: String?,
    val accountNumber: String?,
    val pix: String?,
    val loginUser: String?,
    val accountType: AccountType,
    val description: String?,
    val active: Boolean,
)

enum class AccountType {
    CHECKING_ACCOUNT,
    INVESTMENT,
    NOT_DEFINED,
}

internal fun normalizeAccountName(name: String): String =
    name
        .lowercase()
        .replace(oldValue = " ", newValue = "_")
        .removeAccents()
