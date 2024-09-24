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
      <a href="#over-het-project">Over het project</a>
      <ul>
        <li><a href="#services">Services</a></li>
        <li><a href="#gebouwd-met">Gebouwd met</a></li>
      </ul>
    </li>
    <li>
      <a href="#opstarten">Opstarten</a>
    </li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## Over het project

Er bestaan een aantal C2C-platforms waar consumenten hun tweedehands kleding makkelijk kunnen verkopen aan andere consumenten, grote voorbeelden hiervan zijn Vinted en Marktplaats. Er zijn echter geen platformen die bedrijven de gelegenheid geven tweedehands kleding te verkopen (B2C). Dit komt door het monopolie die C2C-platforms (zoals Vinted) nu hebben op de markt. Deze platformen hoeven zich niet aan dezelfde regelementen te houden als B2C-platforms, aangezien de verkopers in dat geval gebruikers zijn. Dit maakt de C2C-platforms een stuk minder betrouwbaar. Door een platform aan te bieden dat exclusief toegankelijk is voor kringloop- en tweedehands kledingbedrijven, bied je een oplossing om te concurreren met C2C-platforms.

Het project wordt ontwikkeld voor Kringloop013, een commercieel kringloopbedrijf in Tilburg met een grote focus op duurzaamheid. Het platform wat Kringloop013 wil ontwikkelen heeft als doel een betrouwbare en duurzame plek te bieden voor andere kringlopen om hun kleding aan te bieden aan een groter publiek.

### Services
Deze GitHub repository bevat alle backend services die dit platform mogelijk maken.
Een overzicht van deze services:
<ul>
  <li>
    <a href="/product-service/README.md">Product Service</a>
    <ul>
      <li>
        <p>Verantwoordelijk voor alle CRUD operaties op de producten en product afbeeldingen.</p>
      </li>
    </ul>
  </li>
  <li>
    <a href="/api-gateway/README.md">API Gateway</a>
    <ul>
      <li>
        <p>Verantwoordelijk voor het doorsturen van de requests van de gebruikers naar de desbetreffende service.</p>
      </li>
    </ul>
  </li>
  <li>
    <a href="/authentication-service/README.md">Authentication Service</a>
    <ul>
      <li>
        <p>Verantwoordelijk voor het inloggen, uitloggen en registreren van gebruikers.</p>
      </li>
    </ul>
  </li>
</ul>

<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>

### Gebouwd met

Hier een lijst van grote frameworks/technologiën die gebruikt worden binnen dit project
* [![Spring Boot][SpringBoot]][SpringBoot-url]
* [![PostgreSQL][Postgres]][Postgres-url]
* [![MySQL][MySQL]][MySQL-url]
* [![ScyllaDB][ScyllaDB]][ScyllaDB-url]

<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>

## Opstarten

Instructies voor het opstarten van een service zijn te vinden in het gedeelte "Opstarten" in de README van een service.
<ul>
  <li><a href="/product-service/README.md#opstarten">Product Service</a></li>
  <li><a href="/api-gateway/README.md#opstarten">API Gateway</li>
  <li><a href="/authentication-service/README.md#opstarten">Authentication Service</a></li>
</ul>


<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>


## Contact

<!-- (vul hier jullie namen in als je het project overneemt!) -->
#### Groep Najaar 2024 *(huidige groep)*
- Abe Vriens | abevriens.vriens@student.fontys.nl
- Mies van Gogh | mies.vangogh@student.fontys.nl
- May Bossink | b.bossink@student.fontys.nl
- Milo Dorigo | m.dorigo@student.fontys.nl
- Bas de Bruin | bas.debruin@student.fontys.nl

<p align="right">(<a href="#readme-top">terug naar boven</a>)</p>

<!-- LINKS & AFBEELDINGEN -->
[SpringBoot]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[Postgres]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[Postgres-url]: https://www.postgresql.org/
[MySQL]: https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
[MySQL-url]: https://www.mysql.com/
[ScyllaDB]: https://img.shields.io/badge/Scylla%20DB-6CD5E7?style=for-the-badge&logo=scylladb&logoColor=000
[ScyllaDB-url]: https://www.scylladb.com/
