package de.nordakademie.scenario.modell.scenarioModell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.*;

@Entity
@Table(name = "EVENT")
public class Event {

    @Id
    @SequenceGenerator(name = "eventSeq", sequenceName = "event_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "eventSeq")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scenario_id", nullable = false)
    private Scenario scenario;

    private Communication type;

    private int lamportTime;

    @OneToMany(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_EVENT_ID")
    private List<Header> headers = new ArrayList<>();

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "body_id", referencedColumnName = "id")
    private EventBody body;

    private String sourceService = "";

    private String destinationService = "";

    public Event(int lamportTime, List<Header> headers, EventBody body) {
        this.lamportTime = lamportTime;
        this.headers.addAll(headers);
        this.body = body;
    }

    public Event() {
        //no args Constructor
    }

    public int getLamportTime() {
        return lamportTime;
    }

    public void setLamportTime(int lamportTime) {
        this.lamportTime = lamportTime;
    }

    public void addHeader(Header header) {
        headers.add(header);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Communication getType() {
        return type;
    }

    public void setType(Communication type) {
        this.type = type;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public EventBody getBody() {
        return body;
    }

    public void setBody(EventBody body) {
        this.body = body;
    }

    public String getSourceService() {
        return sourceService;
    }

    public void setSourceService(String sourceService) {
        this.sourceService = sourceService;
    }

    public String getDestinationService() {
        return destinationService;
    }

    public void setDestinationService(String destinationService) {
        this.destinationService = destinationService;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (scenario != null ? scenario.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + lamportTime;
        result = 31 * result + (sourceService != null ? sourceService.hashCode() : 0);
        result = 31 * result + (destinationService != null ? destinationService.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Id=" + id +
                ", sourceService='" + sourceService + '\'' +
                ", destinationService='" + destinationService + '\'' +
                '}';
    }
}
