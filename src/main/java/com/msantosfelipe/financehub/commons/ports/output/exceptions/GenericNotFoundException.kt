package com.msantosfelipe.financehub.commons.ports.output.exceptions

class GenericNotFoundException(domainType: String?, id: String?) : RuntimeException(
    "$domainType with id $id not found",
)
