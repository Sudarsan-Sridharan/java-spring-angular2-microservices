package com.soagrowers.pollcommand.handlers;

import com.soagrowers.pollevents.events.PollAddedEvent;
import com.soagrowers.pollevents.events.PollSaleableEvent;
import com.soagrowers.pollevents.events.PollUnsaleableEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * EventHandler's (a.k.a. EventListeners) are used to react to events and perform associated
 * actions.
 * Created by ben on 24/09/15.
 */
@Component
public class EventLoggingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventLoggingHandler.class);
    private static final String IID = String.valueOf(Double.valueOf(Math.random() * 1000).intValue());

    @EventHandler
    public void handle(PollAddedEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}] '{}'", IID, event.getClass().getSimpleName(), event.getId(), event.getName());
    }

    @EventHandler
    public void handle(PollSaleableEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
    }

    @EventHandler
    public void handle(PollUnsaleableEvent event) {
        LOG.debug("Instance:{} EventType:{} EventId:[{}]", IID, event.getClass().getSimpleName(), event.getId());
    }
}






