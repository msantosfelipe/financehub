package com.msantosfelipe.financehub.shared.exceptions

class GenericNotFoundException(domainType: String?, field: String?, value: String?) : RuntimeException(
    "$domainType with $field $value not found.",
)

class GenericAlreadyExistsException(domainType: String?, field: String?, value: String?) : RuntimeException(
    "$domainType with $field $value already exists.",
)
