package com.bettermc.vaultreset.domain.model

import java.time.Duration
import java.time.Instant
import java.util.UUID

data class PlayerVaultRecord(
    val playerUUID: UUID,
    val lastLootedAt: Instant
) {
    private fun getCooldownEnd(cooldownDuration: Duration): Instant {
        return lastLootedAt.plus(cooldownDuration)
    }

    fun canLootAgain(cooldownDuration: Duration, now: Instant = Instant.now()): Boolean {
        return now.isAfter(getCooldownEnd(cooldownDuration))
    }

    fun timeUntilNextLoot(cooldownDuration: Duration, now: Instant = Instant.now()): Duration {
        return Duration.between(now, getCooldownEnd(cooldownDuration))
    }

}
