package com.bettermc.vaultreset.domain.service

import com.bettermc.vaultreset.domain.model.PlayerVaultRecord
import com.bettermc.vaultreset.domain.model.ResetEligibility
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration
import java.time.Instant
import java.util.UUID

class VaultResetServiceTest {

    private val service = VaultResetService()
    private val cooldown = Duration.ofMinutes(1)

    @Test
    fun `should return Ineligible when cooldown has not passed`() {
        val record = PlayerVaultRecord(UUID.randomUUID(), Instant.now())

        val result = service.checkEligibility(record, cooldown)

        assertTrue(result is ResetEligibility.Ineligible)
    }

    @Test
    fun `should return NeverLooted when record is null`() {
        val result = service.checkEligibility(null, cooldown)

        assertTrue(result is ResetEligibility.NeverLooted)
    }

    @Test
    fun `should return Eligible when cooldown has passed`() {
        val record = PlayerVaultRecord(UUID.randomUUID(), Instant.now().minus(Duration.ofMinutes(2)))

        val result = service.checkEligibility(record, cooldown)

        assertTrue(result is ResetEligibility.Eligible)
    }
}