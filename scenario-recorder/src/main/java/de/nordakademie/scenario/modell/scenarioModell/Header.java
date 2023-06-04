package de.nordakademie.scenario.modell.scenarioModell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "HEADER")
public class Header {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "oid", nullable = false)
    @JsonIgnore
    private Event owner;

    public void setOwner(Event owner) {
        this.owner = owner;
    }

    @Column(name = "HeaderKey")
    private String key = ""; //key ist ein verbotenes Wort bei mysql

    @Column(name = "HeaderValue")
    private String headerValue = "";

    public Header(String key, String value) {
        this.key = key;
        this.headerValue = value;
    }

    public Header() {
        //no args default Constructor
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String value) {
        this.headerValue = value;
    }
}
