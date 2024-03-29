# Scenario-Executor

Dieser Scenario-Executor dient der Simulation der Microservice Anwendung und dem Einspielen der aufgenommenen Kommunikation.
Hierfür muss der Scenario-Executor lokal in einer Entwicklungsumgebung gestartet werden.
Dies benötigt eine Java 17 Version.

``mvn spring-boot:run``

Der Scenario-Executor wird auf Port 8081 gestartet und erwartet, den Scenario-Recorder auf Port 8080 zu erreichen.
Hierfür muss der Scenario-Recorder entweder ebenfalls lokal gestartet oder über ein Port-Forwarding erreichbar gemacht werden.

``kubectl port-forward pods/{Name des Scenario-Recorder-Pods} -n debugger 8080:8080``

In der Nutzung durchläuft der Scenario-Executor verschiedene Phasen:
- **Initialisierung**: 
  - Herunterladen des Ausführungsszenarios
  - Registrieren der lokalen Microservices
- **Vorbereitung**:
  - Vorbereiten der Ausführung basierend auf dem Ausführungsszenario und den lokalen Microservices
- **Wiedergabe**
  - Einspielen der Kommunikation in die lokale Entwicklungsumgebung

## Initialisierung

In der Initialisierungsphase ist das Ausführungsszenario zur Wiedergabe aus dem Scenario-Recorder zu exportieren.
Hierfür benötigt der Scenario-Executor eine Verbindung zum Scenario-Recorder innerhalb des Kubernetes-Clusters:

``POST``:``localhost:8081/setup/{Scenario-ID}``

Zudem sind die lokalen zu analysierenden Microservices zu registrieren. Hierfür verfügt der Scenario-Executor über eine Rest-Schnittstelle:

``POST``:``localhost:8081/register``
````json
{
    "name":"example-service",
    "hostUri":"localhost:8082"
}
````

## Vorbereitung

In der Vorbereitungsphase werden die benötigten Requests und Responses des Ausführungsszenarios in ein Wiedergabemodell überführt.
Diese Überführung erfolgt anhand des Ausführungsszenarios und den lokal registrierten Microservices.

``POST``:``localhost:8081/prepare/{Scenario-ID}``

## Wiedergabe

In der Wiedergabephase wird die Kommunikation eingespielt, sodass das Verhalten des registrierten Microservices lokal in einer IDE analysiert werden kann.
Hierfür muss der Microservice so konfiguriert werden, dass alle Requests an den Executor versendet werden.

``POST``:``localhost:8081/execute``
````json
{
  "eventsToReplay": [
    {
      "class": "RequestSendEvent",
      "lamportTime": 0,
      "requestId": "54279c8a-5145-452f-8622-b10477570935",
      "serviceName": "example-service",
      "header": [],
      "path": "example/doPostRequest",
      "httpMethod": "POST",
      "communicationBody": "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}",
      "serviceConfig": {
        "name": "example-service",
        "hostUri": "localhost:8082",
        "fullQualifiedPath": "localhost:8082/"
      }
    },
    {
      "class": "RequestReceiveEvent",
      "lamportTime": 1,
      "requestId": "8968ac79-b664-424d-81e3-deb86021d44a",
      "serviceName": "other-example-service",
      "header": [],
      "path": "example/send/getRequest",
      "httpMethod": "GET"
    }
  ],
  "responses": [
    {
      "requestId": "8968ac79-b664-424d-81e3-deb86021d44a",
      "status": "200",
      "body": "{\"id\":0,\"author\":\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}"
    }
  ]
}
````