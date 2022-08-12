# Banking API Service

* [Development Environment Setup](#development-environment-setup)
* [Running the Application](#running-the-application)
* [Specification](Specification.md)

## Development Environment Setup

- Install JDK 11.x.x
- Install Maven 3.x.x
- Install Docker 20.x.x
- Pull postgres image from docker registry

```shell
  docker pull postgres:14.4
```

- Run downloaded postgres image

```shell
docker run --name banking_postgres -p 5455:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=banking_db -d postgres
```

- Run `scripts/init/init_schema.sql` to setup the database schema.

## Running the Application

Running the Rest API

```shell
mvn spring-boot:run
```

Running the test cases

```shell
mvn test
```

