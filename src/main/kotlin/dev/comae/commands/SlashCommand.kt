package dev.comae.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

interface SlashCommand {
    val command: SlashCommandData
    fun execute(event: SlashCommandInteractionEvent)
}