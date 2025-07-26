package dev.comae.jdaCommandManager.listener

import dev.comae.jdaCommandManager.commands.SlashCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SlashCommandListener(val registry: Map<String, SlashCommand>) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val command = registry[event.name]

        if (command != null) {
            command.execute(event)
        } else {
            event.reply("Unknown Command.").setEphemeral(true).queue()
        }
    }

}