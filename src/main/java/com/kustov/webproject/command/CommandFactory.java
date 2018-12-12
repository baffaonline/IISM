package com.kustov.webproject.command;

import com.kustov.webproject.exception.CommandException;

import java.util.Optional;


/**
 * A factory for creating Command objects.
 */
public class CommandFactory {

    /**
     * Define command.
     *
     * @param commandName the command name
     * @return the optional
     * @throws CommandException the command exception
     */
    public static Optional<Command> defineCommand(String commandName) throws CommandException {
        Optional<Command> command = Optional.empty();
        try {
            if (commandName == null) {
                return command;
            } else {
                CommandType type = CommandType.valueOf(commandName.toUpperCase());
                command = Optional.of(type.getCommand());
            }
        } catch (IllegalArgumentException exc) {
            throw new CommandException(exc);
        }
        return command;
    }
}
