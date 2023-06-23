package de.nordakademie.scenario.modell.scenarioModell;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

@Entity
@Table(name = "SCENARIO")
public class Scenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "scenario")
    //@JoinColumn(name = "FK_SCENARIO_ID")
    private List<Event> events = new ArrayList<>();

    public Scenario(){
        // no args default Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Event> addEvent(Event event) {
        this.events.add(event);
        event.setScenario(this);
        return this.events;
    }

    public int nextLamportTime(){
        return events.size();
    }

    /*public List<String> getAllBodyIds(){
        return requests.stream().map(ScenarioRequest::getBody).map(EventBody::getBody).toList();
    }*/

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scenario scenario = (Scenario) o;

        if (id != scenario.id) return false;
        if (!Objects.equals(name, scenario.name)) return false;
        return Objects.equals(events, scenario.events);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (events != null ? events.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", events=" + events +
                '}';
    }
}
