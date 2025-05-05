package com.msantosfelipe.financehub.commons

import java.text.Normalizer

fun String.removeAccents(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}
