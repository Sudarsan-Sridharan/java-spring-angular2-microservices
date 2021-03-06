package com.soagrowers.pollcommand.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

/**
 * Created by Ben on 07/08/2015.
 */
public class MarkPollAsSaleableCommand {

    /**
     * How does Axon know which Aggregate to Mark as completed? It uses
     * the TargetAggregateIdentifier annotation so that it can get the
     * correct one based on the Id in the annotated field.
     */
    @TargetAggregateIdentifier
    private final String id;

    /**
     * This constructor must set the Id field, otherwise it's unclear
     * to Axon which aggregate this command is intended for.
     *
     * @param id
     */
    public MarkPollAsSaleableCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
