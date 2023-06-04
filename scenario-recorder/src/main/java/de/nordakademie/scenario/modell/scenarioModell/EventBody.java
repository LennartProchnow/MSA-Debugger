package de.nordakademie.scenario.modell.scenarioModell;

import de.nordakademie.scenario.modell.ContentType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "EVENTBODY")
public class EventBody {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long Id;

    @OneToOne(mappedBy = "body", fetch = FetchType.EAGER)
    private Event owner;
    //Diese Information muss wahrscheinlich hier raus und in die einbettende Klasse

    private ContentType contentType;

    private final String bodyId;

    @Lob
    private String body;

    public EventBody(ContentType contentType, String bodyId) {
        this.contentType = contentType;
        this.bodyId = bodyId;
    }

    public EventBody() {
        bodyId = UUID.randomUUID().toString();
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

    //ToDo: Ich könnte in diesem Modell lediglich die obere getBody Methode verwenden und für das Nachladen
    // ein neues Modell bauen, welches diese Methode verwendet
    //public String getPlainBody(){
    //    return this.body;
    //}

    public void setBody(String body){
        this.body = body;
    }

}
