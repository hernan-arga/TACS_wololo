# TACS_wololo

_Trabajo práctico de desarrollo de un juego de captura y defensa de municipios en las provincias argentinas, el cual fue realizado siguiendo el siguiente [enunciado](https://docs.google.com/document/d/e/2PACX-1vS3Bzf0Zs8hZqcmM7khuL21YjMP4maVEZ0NTInGz_tzXdqRadNdIau6jHUEmbE-YyVy-s52BpTtYjIP/pub)._

_El juego debe ser por turnos, su lógica se centrará en 2 o más equipos que luchan por tener el control de una provincia de Argentina. La partida se gana cuando se toma control de todos los municipios de una provincia o un jugador se rinde._

## Comenzando 🚀

_Descargar repositorio mediante git_

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

Jdk 1.8
IDE de java
IDE de typescript
Docker
Mysql
NodeJS



### Instalación 🔧

_Correr imagen de docker_

docker-compose up --build 



## Ejecutando las pruebas ⚙️

_Correr archivos de test ubicados en "\src\test\java\tacs\wololo"._

_Para correr los load tests se tiene dos archivos targets ubicados en testsVegeta: targetsGets.txt y targetsPosts.txt._

_Correr en consola posicionandose en la carpeta testsVegeta:_

_vegeta attack -duration=30s -rate=5 -targets=targetsGets.txt | vegeta report_


## Despliegue 📦

_ToDo_

## Construido con 🛠️


* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Docker](https://docker.com/) - Usado para generar imagenes y containers
* [NewRelic](https://newrelic.com/) - Monitoreo
* [Vegeta](https://github.com/tsenart/vegeta) - Load testing
* [GeoRef](https://datosgobar.github.io/georef-ar-api/) - Informacion Geográfica
* [AsterAPI](https://www.opentopodata.org/datasets/srtm/) - Informacion topológica
* [Pixabay](https://pixabay.com/api/docs/) - Imágenes

## Wiki 📖
ToDo
Puedes encontrar mucho más de cómo utilizar este proyecto en nuestra [Wiki](https://github.com/tu/proyecto/wiki)


## Autores ✒️


* **Matías Berardi**   - [matiasberardi](https://github.com/matiasberardi)
* **Marisol Cervantes**  - [mari967](https://github.com/mari967)
* **Matías Laye** - [matiasga98](https://github.com/Matiasga98)
* **Francisco Peduto** [franchupedu](https://github.com/franchupedu)
* **Hernan Rodríguez Cary** - [hernan-arga](https://github.com/hernan-arga)
* **Melisa Rodríguez** [MelisaRodriguez](https://github.com/MelisaRodriguez)


## Expresiones de Gratitud 🎁

* Gracias a [NicolasAvila89](https://github.com/nicolasAvila89) por guiarnos en el desarrollo.

