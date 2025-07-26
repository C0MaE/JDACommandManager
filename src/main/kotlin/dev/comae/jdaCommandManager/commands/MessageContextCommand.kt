package dev.comae.jdaCommandManager.commands

import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface MessageContextCommand : ContextCommand {
    fun execute(event: MessageContextInteractionEvent)
}