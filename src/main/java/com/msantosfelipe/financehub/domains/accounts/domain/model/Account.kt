package com.msantosfelipe.financehub.domains.accounts.domain.model

import com.msantosfelipe.financehub.shared.removeAccents
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
    val bankCode: String? = null,
    val agency: String? = null,
    val accountNumber: String? = null,
    val pix: String? = null,
    val loginUser: String? = null,
    val accountType: AccountType,
    val description: String? = null,
    val active: Boolean,
)

enum class AccountType {
    CHECKING_ACCOUNT,
    INVESTMENT,
    NOT_DEFINED,
}

internal val accountTypeLabels =
    mapOf(
        AccountType.CHECKING_ACCOUNT to "Conta Corrente",
        AccountType.INVESTMENT to "Investimento",
        AccountType.NOT_DEFINED to "Não Definido",
    )

internal fun normalizeAccountName(name: String): String =
    name
        .lowercase()
        .replace(oldValue = " ", newValue = "_")
        .removeAccents()
