# Kratos_Back
Este es el backend de nuestra la aplicacion Kratos la cual esta diseñada para gestionar laboratorios , reservas , horarios y usuarios.
Esta desarrollada en **Spring Boot** y utiliza **MongoDB** como base de datos.

---

## **Contenido**
1. [Requisitos](#requisitos)
2. [Configuración](#configuración)
4. [Estructura del Proyecto](#estructura-del-proyecto)
5. [Endpoints de la API](#endpoints-de-la-api)
6. [Pruebas](#pruebas)
7. [Integrantes](#integrantes)
---
## **Requisitos**

Para la ejecucion del proyecto necesitamos :

- **Java 17** o superior.
- **JaCoco**(Covertura)
- **JUnit**(Test)
- **MongoDB** instalado y en ejecución.
- **Maven** .
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

Verficamos la estructura del **pom.xml** , en el cual se encuentran todas las dependencias:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.4.3</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>eci.escuelaing.edu.co</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>proyectoCVDS</name>
	<description>Proyecto inicial de la materia CVDS</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>17</java.version>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.source>1.8</maven.compiler.source>
        <sonar.projectKey>library</sonar.projectKey>
        <sonar.projectName>library</sonar.projectName>
        <sonar.host.url>http://localhost:9000</sonar.host.url>
        <sonar.coverage.jacoco.xmlReportPaths>target/site/jacoco/jacoco.xml</sonar.coverage.jacoco.xmlReportPaths>
        <sonar.coverage.exclusions>src//configurators/*</sonar.coverage.exclusions>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-mongodb</artifactId>
			<version>3.2.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.mongodb</groupId>
			<artifactId>mongodb-driver-sync</artifactId>
			<version>5.2.1</version>
		</dependency>
		<dependency>
    		<groupId>org.springframework.boot</groupId>
    		<artifactId>spring-boot-starter-websocket</artifactId>
		</dependency>
		<dependency>
			<groupId>org.mockito</groupId>
			<artifactId>mockito-core</artifactId>
			<version>5.14.2</version>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.jacoco</groupId>
				<artifactId>jacoco-maven-plugin</artifactId>
				<version>0.8.12</version>
				<executions>
					<execution>
						<id>default-prepare-agent</id>
						<goals>
							<goal>prepare-agent</goal>
						</goals>
					</execution>
					<execution>
						<id>report</id>
						<phase>test</phase>
						<goals>
							<goal>report</goal>
						</goals>
						<configuration>
							<excludes>
								<exclude>/configurators/</exclude>
							</excludes>
						</configuration>
					</execution>
					<execution>
						<id>jacoco-check</id>
						<goals>
							<goal>check</goal>
						</goals>
						<configuration>
							<excludes>
								<exclude>**/*ProyectoCvdsApplication.class</exclude>
							</excludes>
							<rules>
								<rule>
									<element>PACKAGE</element>
									<limits>
										<limit>
											<counter>CLASS</counter>
											<value>COVEREDRATIO</value>
											<minimum>0.80</minimum><!--Porcentaje mínimo de cubrimiento para construir el proyecto-->
										</limit>
									</limits>
								</rule>
							</rules>
						</configuration>
					</execution>
				</executions>
			</plugin>
			<plugin>
				<groupId>org.sonarsource.scanner.maven</groupId>
				<artifactId>sonar-maven-plugin</artifactId>
				<version>4.0.0.4121</version>
			</plugin>
			<plugin>
				<groupId>net.serenity-bdd.maven.plugins</groupId>
				<artifactId>serenity-maven-plugin</artifactId>
				<version>3.6.16</version>
				<executions>
					<execution>
						<goals>
							<goal>aggregate</goal>
						</goals>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>

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
El proyecto incluye pruebas para los modelos y servicios asi como algunos de los controladores asi esto no sea necesario:

---
## **Integrantes**

 - Andres Jocobo Sepulveda Sachez
 - Sebastian Julian Villarraga Guerrero
 - Camilo Andrez Fernandez Diaz
 - Roger Alexander Rodriguez
---
