package com.bettermc.vaultreset.domain.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.Duration
import java.time.Instant
import java.util.UUID

class PlayerVaultRecordTest {

    @Test
    fun `should allow loot when cooldown has passed`() {
        // ARRANGE - Un joueur qui a looté il y a X heures.
        // X = Configuration du Cooldown.
        val cooldownDuration = Duration.ofHours(12)
        val timePassed = Instant.now().minus(cooldownDuration.plusHours(1))
        val playerVaultRecord = PlayerVaultRecord(UUID.randomUUID(), timePassed)

        //ACT
        val canLoot: Boolean = playerVaultRecord.canLootAgain(cooldownDuration)

        //ASSERT
        assertTrue(canLoot)
    }

    @Test
    fun `should not be lootable when cooldown has not passed`() {
        val cooldownDuration = Duration.ofHours(12)
        val timePassed = Instant.now().minus(cooldownDuration.minusHours(1))
        val playerVaultRecord = PlayerVaultRecord(UUID.randomUUID(), timePassed)

        val canLoot: Boolean = playerVaultRecord.canLootAgain(cooldownDuration)

        assertFalse(canLoot)
    }

    @Test
    fun `should calculate time remaining correctly`() {
        val now = Instant.now()
        val lastLootedAt = now.minus(Duration.ofHours(10))
        val cooldownDuration = Duration.ofHours(12)

        val record = PlayerVaultRecord(UUID.randomUUID(), lastLootedAt)
        val timeRemaining = record.timeUntilNextLoot(cooldownDuration, now)

        assertEquals(Duration.ofHours(2), timeRemaining)
    }
}

