# TACS_wololo

_Trabajo practico de desarrollo de un juego de captura y defensa de municipios en las provincias argentinas, realizado en base a [enunciado](https://docs.google.com/document/d/e/2PACX-1vS3Bzf0Zs8hZqcmM7khuL21YjMP4maVEZ0NTInGz_tzXdqRadNdIau6jHUEmbE-YyVy-s52BpTtYjIP/pub)._

_El juego debe ser por turnos, su lógica se centrará en 2 o más equipos que luchan por tener el control de una provincia de Argentina. La partida se gana cuando se toma control de todos los municipios de una provincia o un jugador se rinde._

## Comenzando 🚀

_Descargar repositorio mediante git_

Mira **Deployment** para conocer como desplegar el proyecto.


### Pre-requisitos 📋

* Jdk 1.8
* IDE de java
* IDE de typescript
* Docker
* Mysql
* NodeJS



## Despliegue 📦


* En la carpeta raiz del proyecto buildear la imagen ("docker build -t wololo-backend .")
* Luego correr un container basado en esa imagen("docker run --name wololo-backend-container  -p 8080:8080 wololo-backend")
* Entrar a \src\main\ui
* buildear la imagen del front ("docker build -t wololo-frontend.")
* correr un container basado en esa imagen ("docker run --name wololo-front-container  -p 4200:4200 wololo-front")
* En el archivo aplication.properties, es necesario poner la referencia a una Base de datos SQL, para el funcionamiento correcto de la app
* Una vez esten corriendo ambos procesos y la BD este vinculada, la aplicacion deberia ser accesible, con el frontend accesible en la url Localhost:4200




## Ejecutando las pruebas ⚙️

_Correr archivos de test ubicados en "\src\test\java\tacs\wololo"._

_Para correr los load tests se tiene dos archivos targets ubicados en testsVegeta: targetsGets.txt y targetsPosts.txt._

_Correr en consola, posicionandose en testsVegeta:_

_vegeta attack -duration=30s -rate=5 -targets=targetsGets.txt | vegeta report_



## Construido con 🛠️


* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Docker](https://docker.com/) - Usado para generar imagenes y containers
* [NewRelic](https://newrelic.com/) - Monitoreo
* [Vegeta](https://github.com/tsenart/vegeta) - Load testing
* [GeoRef](https://datosgobar.github.io/georef-ar-api/) - Informacion Geográfica
* [AsterAPI](https://www.opentopodata.org/datasets/srtm/) - Informacion topológica
* [Pixabay](https://pixabay.com/api/docs/) - Imágenes

## Wiki 📖
Puedes encontrar mucho más sobre qué decisiones fueron tomadas respecto al código y las soluciones utilizadas en nuestra [Wiki](https://github.com/hernan-arga/TACS_wololo/wiki).


## Autores ✒️


* **Matías Berardi**   - [matiasberardi](https://github.com/matiasberardi)
* **Marisol Cervantes**  - [mari967](https://github.com/mari967)
* **Matías Laye** - [matiasga98](https://github.com/Matiasga98)
* **Francisco Peduto** [franchupedu](https://github.com/franchupedu)
* **Hernan Rodríguez Cary** - [hernan-arga](https://github.com/hernan-arga)
* **Melisa Rodríguez** [MelisaRodriguez](https://github.com/MelisaRodriguez)


## Expresiones de Gratitud 🎁

* Gracias a [NicolasAvila89](https://github.com/nicolasAvila89) por guiarnos en el desarrollo

