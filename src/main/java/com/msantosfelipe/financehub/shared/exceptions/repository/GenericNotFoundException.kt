package com.msantosfelipe.financehub.shared.exceptions.repository

class GenericNotFoundException(domainType: String?, id: String?) : RuntimeException(
    "$domainType with id $id not found",
)
