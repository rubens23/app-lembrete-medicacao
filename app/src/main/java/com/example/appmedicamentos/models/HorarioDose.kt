package com.example.appmedicamentos.models

import java.io.Serializable

data class HorarioDose(
    val horaDose: String,
    val tomouDose: Boolean
): Serializable
