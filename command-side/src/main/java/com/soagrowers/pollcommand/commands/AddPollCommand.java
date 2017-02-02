package com.soagrowers.pollcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Ben on 07/08/2015.
 */
public class AddPollCommand {

    @TargetAggregateIdentifier
    private final String id;
    private final String name;

    public AddPollCommand(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
