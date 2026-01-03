package com.bettermc.vaultreset.infrastructure.bukkit

import com.bettermc.vaultreset.domain.model.PlayerVaultRecord
import com.bettermc.vaultreset.domain.model.ResetEligibility
import com.bettermc.vaultreset.domain.service.VaultResetService
import com.bettermc.vaultreset.infrastructure.config.PluginConfig
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.block.Vault
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import java.time.Instant

class VaultInteractionListener(
    private val pdcAdapter: PdcVaultResetAdapter,
    private val resetService: VaultResetService,
    private val config: PluginConfig
) : Listener {

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        // Ignore off-hand to prevent double events
        if (event.hand != EquipmentSlot.HAND) return
        if (event.action != Action.RIGHT_CLICK_BLOCK) return
        val block = event.clickedBlock ?: return
        if (block.type != Material.VAULT) return

        val vault = block.state as? Vault ?: return
        val player = event.player

        if (!vault.hasRewardedPlayer(player.uniqueId)) return

        val lastResetTime = pdcAdapter.getLastResetTime(vault, player.uniqueId)
        val record = lastResetTime?.let { PlayerVaultRecord(player.uniqueId, it) }

        when (val eligibility = resetService.checkEligibility(record, config.cooldownDuration)) {
            is ResetEligibility.NeverLooted -> {
                // First time seeing this player on this vault - start the cooldown
                pdcAdapter.saveResetTime(vault, player.uniqueId, Instant.now())
                val cooldownText = formatDuration(config.cooldownDuration.toSeconds())
                player.sendActionBar(Component.text("Vault cooldown started: $cooldownText", NamedTextColor.YELLOW))
            }
            is ResetEligibility.Eligible -> {
                resetVault(vault, player)
            }
            is ResetEligibility.Ineligible -> {
                val timeText = formatDuration(eligibility.timeRemaining.toSeconds())
                player.sendActionBar(Component.text("Wait $timeText to reset this vault", NamedTextColor.RED))
            }
        }
    }

    private fun resetVault(vault: Vault, player: Player) {
        vault.removeRewardedPlayer(player.uniqueId)
        vault.update(true, true)

        pdcAdapter.saveResetTime(vault, player.uniqueId, Instant.now())

        player.sendActionBar(Component.text("Vault reset! You can loot it again.", NamedTextColor.GREEN))
    }

    private fun formatDuration(totalSeconds: Long): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        return when {
            hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
            hours > 0 -> "${hours}h"
            minutes > 0 && seconds > 0 -> "${minutes}m ${seconds}s"
            minutes > 0 -> "${minutes}m"
            else -> "${seconds}s"
        }
    }
}