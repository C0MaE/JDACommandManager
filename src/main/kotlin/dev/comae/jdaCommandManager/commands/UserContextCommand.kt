package dev.comae.jdaCommandManager.commands

import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface UserContextCommand : ContextCommand {
    fun execute(event: UserContextInteractionEvent)
}