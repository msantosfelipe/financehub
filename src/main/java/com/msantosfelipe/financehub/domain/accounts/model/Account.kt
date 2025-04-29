package com.msantosfelipe.financehub.domain.accounts.model

import java.util.UUID

data class Account(
    val id: UUID,
    val name: String,
    val bankCode: Int?,
    val agency: Int?,
    val accountNumber: Int?,
    val pix: String?,
    val loginUser: String?,
    val accountType: AccountType,
    val useDescription: String?,
    val active: Boolean
)

enum class AccountType {
    CHECKING_ACCOUNT,
    INVESTMENT,
    NOT_DEFINED
}