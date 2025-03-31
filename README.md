# Kratos_Back
Este es el backend de nuestra la aplicacion Kratos la cual esta diseñada para gestionar laboratorios , reservas , horarios y usuarios.
Esta desarrollada en **Spring Boot** y utiliza **MongoDB** como base de datos.

---

## **Contenido**
1. [Requisitos](#requisitos)
2. [Configuración](#configuración)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Ejecucion](#ejecucion)
6. [Pruebas](#pruebas)
7. [Endpoints de la API](#endpoints-de-la-api)
8. [Cubrimiento de pruebas: Jacoco](#cubrimiento-de-pruebas-jacoco)
9. [Cubrimiento de pruebas: Sonarqube](#cubrimiento-de-pruebas-sonarqube)
10. [Integrantes](#integrantes)
---
## **Requisitos**

Para la ejecucion del proyecto necesitamos :

- **Java 17** o superior.
- **JUnit**(Test)
- **Doker** 
- **Sonar**
- **AzureDevOps**
- **MongoDB Atlas**
- **Maven** con **JaCoco**(Plugin Covertura) y **Mokito**(Dependencia)
- Un IDE como **IntelliJ IDEA**, **Eclipse** o **VS Code** (En nuestro caso IntelliJ IDEA).
---
### Diagrama de Clases

![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/00457606c63bdb7529390a6f99fc6033661faaad/assets/clases.png )

### Diagrama de Componentes

![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/00457606c63bdb7529390a6f99fc6033661faaad/assets/componentes.png)

## **Configuracion**

- Se clona este repositorio:

```text
git clone https://github.com/CamiloFdez/Kratos_Back.git
cd https://github.com/CamiloFdez/Kratos_Back.git
```
- Instalamos las dependencias usando en la terminal  :
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

## **Pruebas**
El proyecto incluye pruebas para los modelos y servicios asi como algunos de los controladores asi esto no sea necesario.

Para la ejecucion de las puebas con maven desde la termianl usamos:

```cmd
mvn test 
```

---

## **EndPoints de la API**

Para probar la API se usara **Postman**, una herramienta para probar APIs RESTful
Antes de todo toca correr la aplicacion en el IDE o desde la terminal con el siguiente comando:

```
mvn spring-boot:run
```
Una vez corriendo la aplicacion podemos probar los endpoints de la API, la base de datos por defecto nos da un http donde pondremos la URI de la base de datos, en este caso es **http://localhost:8080**.

La API tiene los siguientes EndPoints :Primero debemos tener Postman instalado en nuestros dispositivos y luego debemos tener el proyecto corriendo en nuestra maquina local.
Ahora procedemos a realizar las pruebas con los siguientes endPoints:

1. Usuarios:
- GET /usuarios
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Usuariosget.png)
- POST /usuarios
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Usuariospost.png)
- PUT /usuarios
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Usuariosput.png)
- DELETE /usuarios
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Usuariosdelete.png)

2. Laboratorios:
- GET /api/laboratorios
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Labsget.png)
- GET /api/laboratorios/labId/horarios
![image](https://github.com/user-attachments/assets/833fc885-27e4-465c-9209-d583d4007cf0)
- PUT /api/laboratorios/{labId}/horarios
![image](https://github.com/user-attachments/assets/d6a040c2-6fbc-4cc6-82c3-dc89da52d8ec)
- DELETE /api/laboratorios/{labId}/horarios/{horarioId}
![image](https://github.com/user-attachments/assets/5d824afb-0095-4529-a6e4-87bd0534db9d)
- POST /api/laboratorios/{labId}/reservar
![image](https://github.com/user-attachments/assets/c5b36bc6-49f9-482e-b6a0-1e94ef5d12b8)
- POST /api/laboratorios/{labId}/cancelar
![image](https://github.com/user-attachments/assets/9ab46a82-bc29-4fee-8dbc-9a21f8430482)


3. Horarios:
- GET /api/horarios/{labId}
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Horariosget.png)
- PUT /api/horarios/{labId}
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Horariosput.png)

4. Reservas:
- GET /reservas 
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservaget.png)
- POST /reservas
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservapost.png)
- PUT /reservas
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservaput.png)
- DELETE /reservas
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservadelete.png)
- GET /reservas/generar
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservagenerar.png)
- GET /reservas/estadisticas
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Reservaestadistica.png)
---

## **Cubrimiento de pruebas: Jacoco**
Para ver el cubrimiento de pruebas de la aplicacion usamos el plugin de Jacoco, para esto debemos tener el plugin instalado en nuestro IDE y luego ejecutar el siguiente comando en la terminal:

```
mvn clean test jacoco:report
```
Esto generara un reporte en la carpeta **target/site/jacoco** donde podremos ver el cubrimiento de las pruebas realizadas en la aplicacion que se ve de la siguiente manera:
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Jacoco.png)

## **Cubrimiento de pruebas: Sonarqube**
Para ver el cubrimiento de pruebas de la aplicacion usamos el plugin de Sonarqube, para esto debemos tener el plugin instalado en nuestro IDE y luego ejecutar el siguiente comando en la terminal:

```
mvn clean verify sonar:sonar
```
Antes de esto debemos tener el servidor de SonarQube corriendo en nuestra maquina local, para esto debemos descargarlo desde la pagina oficial de SonarQube y tambien descargar docker, Una vez descargado ejecutamos el siguiente comando en la terminal:

```
docker run -d --name ProyetoCVDS -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Esto levantara el servidor de SonarQube en el puerto 9000, luego de esto debemos abrir el navegador y entrar a la siguiente direccion:

```
http://localhost:9000
```

Una vez dentro de la app generaremos un token de acceso para poder usarlo en la aplicacion, para esto vamos a la seccion de **My Account** y luego a **Security** y generamos un token de acceso:
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Sonar2.png)

Una vez generado el token ejecutamos el siguiente comando en la terminal:

```
mvn verify sonar:sonar -D sonar.token=[Token_Generado]
```

Esto generara un reporte en la carpeta **target/sonar** donde podremos ver el cubrimiento de las pruebas realizadas en la aplicacion que se ve de la siguiente manera:
![imagen](https://github.com/CamiloFdez/Kratos_Back/blob/main/assets/Sonar1.png)

## **Integrantes**

- Andrés Jacobo Sepúlveda Sánchez
- Sebastian Julian Villarraga Guerrero
- Camilo Andres Fernandez Diaz
- Roger Alexander Rodriguez
---
