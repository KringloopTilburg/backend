<a id="readme-top"></a>
<br />
<div align="center">
  <a href="https://kringloop013.nl/">
    <img src="https://www.kringlooptilburg.nl/sites/default/files/logo--02.png" alt="Logo" height="150">
  </a>

  <h3 align="center">Kringloop013 | Backend</h3>

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

Volg deze stappen om de authentication service op te starten

### Installeren
De authentication service vereist een aantal programma's om op te kunnen starten.
Zorg ervoor dat je [Docker](https://www.docker.com/) hebt geïnstalleerd
En zorg dat je [Java 21](https://github.com/adoptium/temurin21-binaries/releases/tag/jdk-21.0.4%2B7) en [Maven](https://maven.apache.org/download.cgi) hebt geïnstalleerd.

### Bouwen
Vervolgens bouw je het project.
Start met `mvn clean` om de target directory (de build directory) op te schonen, vervolgens gebruik je `mvn install` om het project te bouwen. Hierna vind je in de `/authentication-service/target/` directory de `authentication-service.jar` als resultaat van de build.

**Let op:** gebruik de maven commando's vanuit de goeie directory, als je vanuit de hoofdmap het commando uitvoert worden alle modules opgeschoond en gebouwd en dat duurt wel ff.

### Docker
Om de authentication service lokaal te draaien hebben we een aantal andere services nodig, hiervoor maken we gebruik van docker compose. In het `docker-compose.yml` bestand vind je de configuratie.
Start deze op met het commando:
`docker compose up`

Daarna start je de authentication service zelf op d.m.v. de Dockerfile. Eerst bouw je deze met het commando `docker build -t authentication-service .` (let op de directory). Vervolgens start je de docker container op met `docker run -d authentication-service`.

<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>
