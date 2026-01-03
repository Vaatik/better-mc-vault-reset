package com.bettermc.vaultreset.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class VaultResetCommand(private val plugin: JavaPlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("§eUsage: /vaultreset reload")
            return true
        }

        when (args[0].lowercase()) {
            "reload" -> {
                plugin.reloadConfig()
                sender.sendMessage("§aVault Reset configuration reloaded!")
            }
            else -> {
                sender.sendMessage("§cUnknown subcommand. Usage: /vaultreset reload")
            }
        }

        return true
    }
}