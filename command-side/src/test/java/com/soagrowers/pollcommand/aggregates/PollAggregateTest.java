package com.soagrowers.pollcommand.aggregates;

import com.soagrowers.pollcommand.commands.AddPollCommand;
import com.soagrowers.pollcommand.commands.MarkPollAsSaleableCommand;
import com.soagrowers.pollcommand.commands.MarkPollAsUnsaleableCommand;
import com.soagrowers.pollevents.events.AbstractEvent;
import com.soagrowers.pollevents.events.PollAddedEvent;
import com.soagrowers.pollevents.events.PollSaleableEvent;
import com.soagrowers.pollevents.events.PollUnsaleableEvent;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ben on 07/08/2015.
 */
public class PollAggregateTest {

    private FixtureConfiguration fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(PollAggregate.class);
    }

    @Test
    public void testAddPoll() throws Exception {
        fixture.given()
                .when(new AddPollCommand("Poll-1", "Poll name"))
                .expectEvents(new PollAddedEvent("Poll-1", "Poll name"));
    }

    @Test
    public void testMarkPollItemAsSaleable() throws Exception {
        fixture.given(new PollAddedEvent("Poll-2", "Poll name"))
                .when(new MarkPollAsSaleableCommand("Poll-2"))
                .expectVoidReturnType()
                .expectEvents(new PollSaleableEvent("Poll-2"));
    }

    @Test
    public void testMarkPollItemAsUnsaleableIsAllowed() throws Exception {
        List<AbstractEvent> events = new ArrayList<AbstractEvent>();
        events.add(new PollAddedEvent("Poll-3", "Poll name"));
        events.add(new PollSaleableEvent("Poll-3"));

        fixture.given(events)
                .when(new MarkPollAsUnsaleableCommand("Poll-3"))
                .expectVoidReturnType()
                .expectEvents(new PollUnsaleableEvent("Poll-3"));
    }

    @Test
    public void testMarkPollItemAsUnsaleableIsPrevented() throws Exception {
        List<AbstractEvent> events = new ArrayList<AbstractEvent>();
        events.add(new PollAddedEvent("Poll-3", "Poll name"));

        fixture.given(events)
                .when(new MarkPollAsUnsaleableCommand("Poll-3"))
                .expectException(IllegalStateException.class);
    }
}
