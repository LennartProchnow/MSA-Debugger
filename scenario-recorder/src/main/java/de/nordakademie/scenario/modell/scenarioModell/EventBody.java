package de.nordakademie.scenario.modell.scenarioModell;

import de.nordakademie.scenario.modell.ContentType;
import jakarta.persistence.*;

@Entity
@Table(name = "EVENTBODY")
public class EventBody {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    private ContentType contentType;

    @Lob
    private String body;

    public EventBody(ContentType contentType) {
        this.contentType = contentType;
    }

    public EventBody() {
        //no args constructor
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }


    public void setBody(String body){
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventBody eventBody = (EventBody) o;

        return Id == eventBody.Id;
    }

    @Override
    public int hashCode() {
        return (int) (Id ^ (Id >>> 32));
    }
}
