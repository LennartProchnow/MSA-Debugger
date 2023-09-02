package de.nordakademie.scenario.modell.scenarioModell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "HEADER")
public class Header {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId(){
        return this.id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Header header = (Header) o;

        return id == header.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
