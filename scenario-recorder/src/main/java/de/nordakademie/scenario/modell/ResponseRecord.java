package de.nordakademie.scenario.modell;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ResponseRecord {

    @JsonAlias(":status")
    private String status;

    @JsonAlias(":content-type")
    private String contentType;

    private String body;

    public ResponseRecord(String status, String contentType, String body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    public ResponseRecord() {
        //Default no args Constructor
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
