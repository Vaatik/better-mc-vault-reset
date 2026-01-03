package com.bettermc.vaultreset.domain.model
import java.time.Duration

sealed class ResetEligibility {
    object Eligible : ResetEligibility()
    data class Ineligible(val timeRemaining: Duration) : ResetEligibility()
    object NeverLooted: ResetEligibility()
}