# Kratos_Back
Este es el backend de nuestra la aplicacion Kratos la cual esta diseñada para gestionar laboratorios , reservas , horarios y usuarios.
Esta desarrollada en **Spring Boot** y utiliza **MongoDB** como base de datos.

---

## **Contenido**
1. [Requisitos](#requisitos)
2. [Configuración](#configuración)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Ejecucion](#ejecucion)
5. [Endpoints de la API](#endpoints-de-la-api)
6. [Pruebas](#pruebas)
7. [Integrantes](#integrantes)
---
## **Requisitos**

Para la ejecucion del proyecto necesitamos :

- **Java 17** o superior.
- **JUnit**(Test)
- **Doker** 
- **Sonar**
- **AzureDevOps**
- **MongoDB** instalado y en ejecución.
- **Maven** con **JaCoco**(Plugin Covertura) y **Mokito**(Dependencia)
- Un IDE como **IntelliJ IDEA**, **Eclipse** o **VS Code** (En nuestro caso IntelliJ IDEA).
---
## **Configuracion**

Inicialmente se creo un proyecto nuevo con la ayuda de **Spring Initializr** de la siguiente forma:

![imagen](https://raw.githubusercontent.com/CamiloFdez/Kratos_Back/master/assets/SpringInitialzr.png)

Luego configuramos la base de datos creando una cuenta en **Mongo Atlas** y creando una base de datos:

![imagen](https://raw.githubusercontent.com/CamiloFdez/Kratos_Back/master/assets/MongoAtlas.png)

Conectamos la base de datos con la aplicacion agragando la URI en las **application.properties** de nuestra aplicacion :

```Spring
spring.data.mongodb.uri=mongodb+srv://rogerrodriguez:Ro187ro.@proyectocvds.g9f0x.mongodb.net/sample_mflix?retryWrites=true&w=majority&appName=proyectoCVDS&ssl=true
```
Instalamos las dependencias usando en la terminal  :

```text
./mvn clean Install
```

---

## **Estructura del proyecto**

El proyecto esta organizado de la siguiente manera:

```text
   src
├── main
│   ├── java
│   │   └── eci
│   │       └── escuelaing
│   │           └── edu
│   │               └── co
│   │                   ├── controllers       # Controladores de la API
│   │                   ├── models            # Modelos de datos
│   │                   ├── repositories      # Repositorios de MongoDB
│   │                   ├── services          # Lógica de negocio
│   │                   └── ProyectoCvdsApplication.java  # Clase principal
│   └── resources
│       └── application.properties            # Configuración de la aplicación
└── test
    └── java
        └── eci
            └── escuelaing
                └── edu
                    └── co
                        └── models            # Pruebas unitarias
```
---
## **Ejecucion**

Para ejecutar la aplicacion con maven desde la terminal :
```txt
./mvnw spring-boot:run

```
Desde el IDE ejecutamos la clase **ProyectoCvdsApplication** .

---

## **EndPoints de la API**

La API tiene los siguientes EndPoints :

### Laboratorios

```
- GET /api/laboratorios/: Obtiene todos los laboratorios.

- GET /api/laboratorios/{labId}/horarios: Obtiene los horarios de un laboratorio.

- PUT /api/laboratorios/{labId}/horarios: Actualiza un horario de un laboratorio.

- DELETE /api/laboratorios/{labId}/horarios/{horarioId}: Elimina un horario de un laboratorio.

- POST /api/laboratorios/{labId}/reservar: Reserva un laboratorio.

- POST /api/laboratorios/{labId}/cancelar: Cancela una reserva de un laboratorio. 
```

### Hararios

```
GET /api/horarios/{labId}: Obtiene los horarios de un laboratorio.

PUT /api/horarios/{labId}: Actualiza un horario.
```

### Reservas

```
- GET /reservas: Obtiene todas las reservas.

- GET /reservas/{fechaHora}: Obtiene una reserva por fecha y hora.

- POST /reservas: Crea una nueva reserva.

- PUT /reservas/{fechaHora}: Actualiza una reserva.

- DELETE /reservas/{fechaHora}: Elimina una reserva.
```

### Uusarios
```

GET /usuarios: Obtiene todos los usuarios.

GET /usuarios/{id}: Obtiene un usuario por ID.

POST /usuarios: Crea un nuevo usuario.

PUT /usuarios/{id}: Actualiza un usuario.

DELETE /usuarios/{id}: Elimina un usuario.
```

---

## **Pruebas**
El proyecto incluye pruebas para los modelos y servicios asi como algunos de los controladores asi esto no sea necesario.

Para la ejecucion de las puebas con maven desde la termianl usamos:

```cmd
mvn test 
```

---
## **Integrantes**

 - Andres Jocobo Sepulveda Sachez
 - Sebastian Julian Villarraga Guerrero
 - Camilo Andrez Fernandez Diaz
 - Roger Alexander Rodriguez
---
