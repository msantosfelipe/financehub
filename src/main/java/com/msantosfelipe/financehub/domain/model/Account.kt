package com.msantosfelipe.financehub.domain.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty
import io.micronaut.serde.annotation.Serdeable
import java.util.UUID

@MappedEntity(value = "accounts")
@Serdeable
data class Account(
    @field:Id
    @field:GeneratedValue(GeneratedValue.Type.AUTO)
    val id: UUID? = null,
    val name: String,
    val bankCode: Int?,
    val agency: Int?,
    val accountNumber: Int?,
    val pix: String?,
    val loginUser: String?,
    val accountType: AccountType,
    @MappedProperty("description")
    val useDescription: String?,
    val active: Boolean,
)

enum class AccountType {
    CHECKING_ACCOUNT,
    INVESTMENT,
    NOT_DEFINED,
}
