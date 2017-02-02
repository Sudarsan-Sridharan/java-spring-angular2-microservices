package com.soagrowers.pollcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Sets the ToDoItem to reopened.
 *
 * @author robertgolder
 */
public class MarkPollAsUnsaleableCommand {

    @TargetAggregateIdentifier
    private final String id;

    /**
     * This constructor must set the Id field, otherwise it's unclear
     * to Axon which aggregate this command is intended for.
     *
     * @param id
     */
    public MarkPollAsUnsaleableCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
