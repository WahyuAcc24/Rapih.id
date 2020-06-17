package com.Rapih.id.Model

import java.io.Serializable

data class UserLocation(
    var latLong: Map<String, Double> = mapOf(),
    var address: String= ""
): Serializable