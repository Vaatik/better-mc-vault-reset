package com.bettermc.vaultreset.infrastructure.config

import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration

class PluginConfig(private val plugin: JavaPlugin) {
    val cooldownDuration: Duration get() = parseDuration(plugin.config.getString("cooldown", "24h")!!)

    private fun parseDuration(value: String): Duration {
        val regex = Regex("(\\d+)([smhd])")
        val match = regex.find(value) ?: return Duration.ofHours(24)

        val amount = match.groupValues[1].toLong()
        val unit = match.groupValues[2]

        return when (unit) {
            "s" -> Duration.ofSeconds(amount)
            "m" -> Duration.ofMinutes(amount)
            "h" -> Duration.ofHours(amount)
            "d" -> Duration.ofDays(amount)
            else -> Duration.ofHours(24)
        }
    }

}