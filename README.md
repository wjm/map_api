## Run the API

```bash
./mvnw spring-boot:run
```

Then the API will be running at [http://localhost:8080](http://localhost:8080)


Swagger ui will be at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## build docker

```bash
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=IMAGENAME
```

## Usage

Change 

 - spring.datasource.url=
 - spring.datasource.username=
 - spring.datasource.password=

for database connection.