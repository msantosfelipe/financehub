package com.msantosfelipe.financehub.adapters.input.rest.dto

import com.msantosfelipe.financehub.domain.model.AccountType
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CreateAccountRequest(
    val displayName: String,
    val bankCode: String?,
    val agency: String?,
    val accountNumber: String?,
    val pix: String?,
    val loginUser: String?,
    val accountType: AccountType?,
    val useDescription: String?,
    val active: Boolean?,
)

@Serdeable
data class UpdateAccountRequest(
    val bankCode: String?,
    val agency: String?,
    val accountNumber: String?,
    val pix: String?,
    val loginUser: String?,
    val accountType: AccountType,
    val useDescription: String?,
    val active: Boolean,
)
