package com.msantosfelipe.financehub.shared.exceptions

class GenericException(message: String?) : RuntimeException(message)

class GenericNotFoundException(domainType: String?, field: String?, value: String?) : RuntimeException(
    "$domainType with $field $value not found.",
)

class GenericAlreadyExistsException(domainType: String?, field: String?, value: String?) : RuntimeException(
    "$domainType with $field $value already exists.",
)
