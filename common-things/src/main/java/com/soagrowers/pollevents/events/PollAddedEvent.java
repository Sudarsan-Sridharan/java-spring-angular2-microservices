package com.soagrowers.pollevents.events;


public class PollAddedEvent extends AbstractEvent {


    private String name;

    public PollAddedEvent() {
    }

    public PollAddedEvent(String id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
