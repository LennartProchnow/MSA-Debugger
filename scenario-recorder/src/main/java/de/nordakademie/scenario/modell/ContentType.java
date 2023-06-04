package de.nordakademie.scenario.modell;

public enum ContentType {
    APPLICATION_JSON("application/json"),
    PLAIN_TEXT("plain/Text"),
    TEXT_HTML("text/html");

    String value;
    ContentType(String value) {
        this.value = value;
    }

    public final String getValue(){
        return this.value;
    }

    public static ContentType getEnum(String value) {
        switch (value) {
            case "application/json":
                return APPLICATION_JSON;
            case "plain/Text":
                return PLAIN_TEXT;
            case "text/html":
                return TEXT_HTML;
            default:
                throw new NullPointerException("no value:" + value);
        }
    }

}
