package dev.comae.jdaCommandManager.manager

import dev.comae.jdaCommandManager.commands.ContextCommand
import dev.comae.jdaCommandManager.commands.SlashCommand
import dev.comae.jdaCommandManager.listener.ContextCommandListener
import dev.comae.jdaCommandManager.listener.SlashCommandListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.interactions.commands.Command
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction
import org.reflections.Reflections
import java.lang.reflect.Modifier


class CommandManager(val commandPackage: String, val jda: JDA) {

    var commands: CommandListUpdateAction = jda.updateCommands()

    val slashCommandRegistry = mutableMapOf<String, SlashCommand>()
    val contextCommandRegistry = mutableMapOf<String, ContextCommand>()

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

    fun registerAllContextCommands(): CommandManager {
        val reflections = Reflections(commandPackage)

        val contextCommands = reflections.getSubTypesOf(ContextCommand::class.java).filter {
            !Modifier.isAbstract(it.modifiers) && !Modifier.isInterface(it.modifiers)
        }.mapNotNull { clazz ->
            try {
                val ctor = clazz.getDeclaredConstructor()
                ctor.isAccessible = true
                ctor.newInstance()
            } catch (e: Exception) {
                println("Error instantiating ${clazz.name}: ${e.message}")
                null
            }
        }

        println(contextCommands)

        contextCommands.forEach { command -> contextCommandRegistry[command.command.name] = command }

        return this
    }

    fun queue() {
        jda.addEventListener(SlashCommandListener(slashCommandRegistry))
        jda.addEventListener(ContextCommandListener(contextCommandRegistry))

        commands.addCommands(slashCommandRegistry.map { entry -> entry.value.command })
        commands.addCommands(contextCommandRegistry.map { entry -> entry.value.command })

        commands.queue()
    }

}
