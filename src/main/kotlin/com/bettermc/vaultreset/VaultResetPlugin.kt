package com.bettermc.vaultreset

import com.bettermc.vaultreset.command.VaultResetCommand
import com.bettermc.vaultreset.domain.service.VaultResetService
import com.bettermc.vaultreset.infrastructure.bukkit.PdcVaultResetAdapter
import com.bettermc.vaultreset.infrastructure.bukkit.VaultInteractionListener
import com.bettermc.vaultreset.infrastructure.config.PluginConfig
import org.bukkit.plugin.java.JavaPlugin

class VaultResetPlugin : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()

        val config = PluginConfig(this)
        val pdcAdapter = PdcVaultResetAdapter(this)
        val resetService = VaultResetService()

        server.pluginManager.registerEvents(
            VaultInteractionListener(pdcAdapter, resetService, config),
            this
        )

        getCommand("vaultreset")?.setExecutor(VaultResetCommand(this))

        logger.info("Better MC Vault Reset enabled!")
        logger.info("Cooldown configured: ${config.cooldownDuration.toHours()} hours")
    }

    override fun onDisable() {
        logger.info("Better MC Vault Reset disabled!")
    }
}
