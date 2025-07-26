package dev.comae.jdaCommandManager.listener

import dev.comae.jdaCommandManager.commands.MessageContextCommand
import dev.comae.jdaCommandManager.commands.UserContextCommand
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ContextCommandListener(val registry: Map<String, Any>) : ListenerAdapter() {

    override fun onUserContextInteraction(event: UserContextInteractionEvent) {
        val command = registry[event.name]

        if (command != null && command is UserContextCommand) {
            command.execute(event)
        } else {
            event.reply("Unknown Command.").setEphemeral(true).queue()
        }
    }

    override fun onMessageContextInteraction(event: MessageContextInteractionEvent) {
        val command = registry[event.name]

        if (command != null && command is MessageContextCommand) {
            command.execute(event)
        } else {
            event.reply("Unknown Command.").setEphemeral(true).queue()
        }
    }

}