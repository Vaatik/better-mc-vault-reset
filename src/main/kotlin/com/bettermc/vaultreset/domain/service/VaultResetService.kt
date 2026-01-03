package com.bettermc.vaultreset.domain.service

import com.bettermc.vaultreset.domain.model.PlayerVaultRecord
import com.bettermc.vaultreset.domain.model.ResetEligibility
import java.time.Duration

class VaultResetService {
    fun checkEligibility(record: PlayerVaultRecord?, cooldownDuration: Duration): ResetEligibility {
        if (record == null) {
            return ResetEligibility.NeverLooted
        }

        if (record.canLootAgain(cooldownDuration)) {
            return ResetEligibility.Eligible
        } else {
            val timeRemaining = record.timeUntilNextLoot(cooldownDuration)
            return ResetEligibility.Ineligible(timeRemaining)
        }
    }
}