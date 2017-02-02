package com.soagrowers.pollquery.handlers;

import com.soagrowers.pollevents.events.PollAddedEvent;
import com.soagrowers.pollevents.events.PollSaleableEvent;
import com.soagrowers.pollevents.events.PollUnsaleableEvent;
import com.soagrowers.pollquery.domain.Poll;
import com.soagrowers.pollquery.repository.PollRepository;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Ben on 10/08/2015.
 */
@Component
public class PollViewEventHandler implements ReplayAware {

    private static final Logger LOG = LoggerFactory.getLogger(PollViewEventHandler.class);

    @Autowired
    private PollRepository pollRepository;

    @EventHandler
    public void handle(PollAddedEvent event) {
        LOG.info("PollAddedEvent: [{}] '{}'", event.getId(), event.getName());
        pollRepository.save(new Poll(event.getId(), event.getName(), false));
    }

    @EventHandler
    public void handle(PollSaleableEvent event) {
        LOG.info("PollSaleableEvent: [{}]", event.getId());
        if (pollRepository.exists(event.getId())) {
            Poll poll = pollRepository.findOne(event.getId());
            if (!poll.isSaleable()) {
                poll.setSaleable(true);
                pollRepository.save(poll);
            }
        }
    }

    @EventHandler
    public void handle(PollUnsaleableEvent event) {
        LOG.info("PollUnsaleableEvent: [{}]", event.getId());

        if (pollRepository.exists(event.getId())) {
            Poll poll = pollRepository.findOne(event.getId());
            if (poll.isSaleable()) {
                poll.setSaleable(false);
                pollRepository.save(poll);
            }
        }
    }

    public void beforeReplay() {
        LOG.info("Event Replay is about to START. Clearing the View...");
    }

    public void afterReplay() {
        LOG.info("Event Replay has FINISHED.");
    }

    public void onReplayFailed(Throwable cause) {
        LOG.error("Event Replay has FAILED.");
    }
}
