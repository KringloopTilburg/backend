<a id="readme-top"></a>
<br />
<div align="center">
  <a href="https://kringloop013.nl/">
    <img src="https://www.kringlooptilburg.nl/sites/default/files/logo--02.png" alt="Logo" height="150">
  </a>

  <h3 align="center">Kringloop013 | Product Service</h3>

  <p align="center">
    Backend project voor het duurzaamste en betrouwbaarste kringloop platform ooit.
  </p>
</div>

<details>
  <summary>Inhoudsopgave</summary>
  <ol>
    <li>
      <a href="#opstarten">Opstarten</a>
    </li>
  </ol>
</details>

## Opstarten

Volg deze stappen om de productservice op te starten

### Installeren
De product service vereist een aantal programma's om op te kunnen starten.
Zorg ervoor dat je [Docker](https://www.docker.com/) hebt geïnstalleerd
En zorg dat je [Java 21](https://github.com/adoptium/temurin21-binaries/releases/tag/jdk-21.0.4%2B7) en [Maven](https://maven.apache.org/download.cgi) hebt geïnstalleerd.

### Configuratie
De product service maakt gebruik van een aantal environment variables om te kunnen draaien.
Deze kun je instellen door in de hoofdmap een `.env` bestand aan te maken met de volgende variabelen (vul zelf de correcte waarden in):
```
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/kringloop
SPRING_DATASOURCE_USERNAME=kringloop
SPRING_DATASOURCE_PASSWORD=kringloop
RABBITMQ_URL=amqp://localhost
```

### Bouwen
Vervolgens bouw je het project.
Start met `mvn clean` om de target directory (de build directory) op te schonen, vervolgens gebruik je `mvn install` om het project te bouwen. Hierna vind je in de `/product-service/target/` directory de `product-service.jar` als resultaat van de build.

**Let op:** gebruik de maven commando's vanuit de goeie directory, als je vanuit de hoofdmap het commando uitvoert worden alle modules opgeschoond en gebouwd en dat duurt wel ff.

### Docker
Om de product service lokaal te draaien hebben we een aantal andere services nodig, hiervoor maken we gebruik van docker compose. In het `docker-compose.yml` bestand vind je de configuratie.
Start deze op met het commando:
`docker compose up`

Daarna start je de product service zelf op d.m.v. de Dockerfile. Eerst bouw je deze met het commando `docker build -t product-service .` (let op de directory). Vervolgens start je de docker container op met `docker run -d product-service`.

### IntelliJ IDEA gebruiken
Maak je gebruik van de IntelliJ IDEA IDE, dan kun je de product service starten door de `ProductServiceApplication` klasse te runnen of door een Build configuratie toe te voegen en Spring Boot te selecteren, je hoeft dan geen Docker te gebruiken.

Let wel op dat je hier de environment variables goed instelt, dit doe je door je build configuratie aan te passen en de environment variables in te stellen.

<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>
