# Scenario-Recorder

Der Scenario-Recorder dient der Speicherung und der Aufnahme der Kommunikation innerhalb der Microservice-Anwendung in Form eines Ausführungsszenarios.

## Lokales Starten des Scenario-Recorders

Der Scenario-Recorder kann folgender Maßen gestartet werden.
```shell script
./mvnw compile quarkus:dev
```

Dies benötigt eine Java 17 Version.

Zum Starten muss eine MariaDB gestartet werden:
``docker pull mariadb``

gestartet werden muss die MariaDB mit folgenden Parametern:
- Port: 3306
- DBName: SCENARIO_DB
- root_password: secret

Anschließend fährt der Scenario-Recorder lokal hoch und hörcht auf Port 8080. 
Dies kann in der ``scr/main/resources/application.properties`` Datei umkonfiguriert werden. 

## Nutzung

**Starten der Aufnahme**

Das Starten der Aufnahme der Kommunikation zwischen den Microservices muss manuell angestoßen werden:

``POST``:``localhost:8080/scenario/record/start``

Anschließend können Requests und Responses persistiert werden.

**Aufnehmen von Requests**

``POST``:``localhost:8080/scenario/request/``
````json
{
  ":authority":"ratings:9080",
  ":path":"/ratings/0",
  ":method":"GET",
  ":scheme":"http",
  "x-request-id":"f41b49b4-9d18-9f1d-bf62-b09a33c7d417",
  "x-b3-traceid":"abc8b3c03ab1e767f63605d0fefb2655",
  "x-b3-spanid":"f36cc92e905beb5f",
  "x-b3-parentspanid":"bc3ca07250a7acc4",
  "x-source-service-name":"MockService",
  "x-communication-id":"123142123",
  "body":""
}
````

**Aufnehmen von Responses**

``POST``:``localhost:8080/scenario/response``
````json
{
  ":status":"200",
  ":content-type":"application/json",
  "body":"{\"id\":0,'author':\"William Shakespeare\",\"year\":1595,\"type\":\"paperback\",\"pages\":200,\"publisher\":\"PublisherA\",\"language\":\"English\",\"ISBN-10\":\"1234567890\",\"ISBN-13\":\"123-1234567890\"}"
}
````

**Lesen eines Ausführungsszenarios**

``GET``:``localhost:8080/scenario/{Szenario-ID}``

**Stoppen der Aufnahme**

``GET``:``http://localhost:8080/scenario/record/stop/{Szenario-ID}``