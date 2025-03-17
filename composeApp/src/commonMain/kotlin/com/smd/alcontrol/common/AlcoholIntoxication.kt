package com.smd.alcontrol.common

enum class AlcoholIntoxication(val minBac: Double,val maxBac: Double) {
    NONE(0.0, 0.0),
    SAFE_LEVEL(0.1, 0.3),
    MILD_INTOXICATION(0.3, 0.5),
    INTOXICATION(0.5, 1.5),
    SERVE_INTOXICATION(1.5, 2.5),
    HARD_INTOXICATION(2.5, 3.5),
    RISK_OF_DEATH(3.5, Double.MAX_VALUE)
}