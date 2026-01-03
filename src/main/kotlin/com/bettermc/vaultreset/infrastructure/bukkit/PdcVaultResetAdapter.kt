package com.bettermc.vaultreset.infrastructure.bukkit

import org.bukkit.NamespacedKey
import org.bukkit.block.TileState
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import java.time.Instant
import java.util.UUID

class PdcVaultResetAdapter(private val plugin: Plugin) {

    fun getLastResetTime(vault: TileState, playerId: UUID): Instant? {
        val pdc = vault.persistentDataContainer
        val key = createKey(playerId)
        val timestamp = pdc.get(key, PersistentDataType.LONG)

        return timestamp?.let { Instant.ofEpochMilli(it) }
    }

    fun saveResetTime(vault: TileState, playerId: UUID, time: Instant) {
        val pdc = vault.persistentDataContainer
        val key = createKey(playerId)

        pdc.set(key, PersistentDataType.LONG, time.toEpochMilli())

        vault.update()
    }

    private fun createKey(playerId: UUID): NamespacedKey {
        return NamespacedKey(plugin, "reset_$playerId")
    }
}
