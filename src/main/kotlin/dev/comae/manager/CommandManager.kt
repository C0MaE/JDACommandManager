package dev.comae.manager

import dev.comae.commands.SlashCommand
import dev.comae.listener.SlashCommandListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.reflections.Reflections


class CommandManager(val commandPackage: String, val jda: JDA) {

    var commands: CommandListUpdateAction = jda.updateCommands()

    val slashCommandRegistry = mutableMapOf<String, SlashCommand>()

    fun registerAllSlashCommands(): CommandManager {
        val reflections = Reflections(commandPackage)

        val slashCommands = reflections.getSubTypesOf(SlashCommand::class.java).mapNotNull { clazz ->
            try {
                val ctor = clazz.getDeclaredConstructor()
                ctor.isAccessible = true
                ctor.newInstance()
            } catch (e: Exception) {
                println("Error instantiating ${clazz.name}: ${e.message}")
                null
            }
        }

        slashCommands.forEach { command -> slashCommandRegistry[command.command.name] = command }

        return this
    }

    fun queue() {
        jda.addEventListener(SlashCommandListener(slashCommandRegistry))

        commands.addCommands(slashCommandRegistry.map { entry -> entry.value.command })

        commands.queue()
    }

}
