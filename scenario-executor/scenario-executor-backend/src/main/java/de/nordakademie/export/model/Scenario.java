package de.nordakademie.export.model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String id;

    private String name;

    private List<Event> events = new ArrayList<>();

    public Scenario(String id, String name, List<Event> events) {
        this.id = id;
        this.name = name;
        this.events = events;
    }

    public Scenario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
