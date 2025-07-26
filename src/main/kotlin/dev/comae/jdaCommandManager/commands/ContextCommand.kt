package dev.comae.jdaCommandManager.commands

import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface ContextCommand {
    val command: CommandData
}