package com.soagrowers.pollcommand.aggregates;

import com.soagrowers.pollcommand.commands.AddPollCommand;
import com.soagrowers.pollcommand.commands.MarkPollAsSaleableCommand;
import com.soagrowers.pollcommand.commands.MarkPollAsUnsaleableCommand;
import com.soagrowers.pollevents.events.PollAddedEvent;
import com.soagrowers.pollevents.events.PollSaleableEvent;
import com.soagrowers.pollevents.events.PollUnsaleableEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PollAggregate is essentially a DDD AggregateRoot (from the DDD concept). In event-sourced
 * systems, Aggregates are often stored and retreived using a 'Repository'. In the
 * simplest terms, Aggregates are the sum of their applied 'Events'.
 * <p/>
 * The Repository stores the aggregate's Events in an 'Event Store'. When an Aggregate
 * is re-loaded by the repository, the Repository re-applies all the stored events
 * to the aggregate thereby re-creating the logical state of the Aggregate.
 * <p/>
 * The PollAggregate Aggregate can handle and react to 'Commands', and when it reacts
 * to these com.soagrowers.Poll.commands it creates and 'applies' Events that represent the logical changes
 * to be made. These Events are also handled by the PollAggregate.
 * <p/>
 * Axon takes care of much of this via the CommandBus, EventBus and Repository.
 * <p/>
 * Axon delivers com.soagrowers.Poll.commands placed on the bus to the Aggregate. Axon supports the 'applying' of
 * Events to the Aggregate, and the handling of those events by the aggregate or any other
 * configured EventHandlers.
 */
public class PollAggregate extends AbstractAnnotatedAggregateRoot {

    private static final Logger LOG = LoggerFactory.getLogger(PollAggregate.class);

    /**
     * Aggregates that are managed by Axon must have a unique identifier.
     * Strategies similar to GUID are often used. The annotation 'AggregateIdentifier'
     * identifies the id field as such.
     */
    @AggregateIdentifier
    private String id;
    private String name;
    private boolean isSaleable = false;

    /**
     * This default constructor is used by the Repository to construct
     * a prototype PollAggregate. Events are then used to set properties
     * such as the PollAggregate's Id in order to make the Aggregate reflect
     * it's true logical state.
     */
    public PollAggregate() {
    }

    /**
     * This constructor is marked as a 'CommandHandler' for the
     * AddPollCommand. This command can be used to construct
     * new instances of the Aggregate. If successful a new PollAddedEvent
     * is 'applied' to the aggregate using the Axon 'apply' method. The apply
     * method appears to also propagate the Event to any other registered
     * 'Event Listeners', who may take further action.
     *
     * @param command
     */
    @CommandHandler
    public PollAggregate(AddPollCommand command) {
        LOG.debug("Command: 'AddPollCommand' received.");
        LOG.debug("Queuing up a new PollAddedEvent for Poll '{}'", command.getId());
        apply(new PollAddedEvent(command.getId(), command.getName()));
    }

    @CommandHandler
    public void markSaleable(MarkPollAsSaleableCommand command) {
        LOG.debug("Command: 'MarkPollAsSaleableCommand' received.");
        if (!this.isSaleable()) {
            apply(new PollSaleableEvent(id));
        } else {
            throw new IllegalStateException("This PollAggregate (" + this.getId() + ") is already Saleable.");
        }
    }

    @CommandHandler
    public void markUnsaleable(MarkPollAsUnsaleableCommand command) {
        LOG.debug("Command: 'MarkPollAsUnsaleableCommand' received.");
        if (this.isSaleable()) {
            apply(new PollUnsaleableEvent(id));
        } else {
            throw new IllegalStateException("This PollAggregate (" + this.getId() + ") is already off-sale.");
        }
    }

    /**
     * This method is marked as an EventSourcingHandler and is therefore used by the Axon framework to
     * handle events of the specified type (PollAddedEvent). The PollAddedEvent can be
     * raised either by the constructor during PollAggregate(AddPollCommand) or by the
     * Repository when 're-loading' the aggregate.
     *
     * @param event
     */
    @EventSourcingHandler
    public void on(PollAddedEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        LOG.debug("Applied: 'PollAddedEvent' [{}] '{}'", event.getId(), event.getName());
    }

    @EventSourcingHandler
    public void on(PollSaleableEvent event) {
        this.isSaleable = true;
        LOG.debug("Applied: 'PollSaleableEvent' [{}]", event.getId());
    }

    @EventSourcingHandler
    public void on(PollUnsaleableEvent event) {
        this.isSaleable = false;
        LOG.debug("Applied: 'PollUnsaleableEvent' [{}]", event.getId());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSaleable() {
        return isSaleable;
    }
}
