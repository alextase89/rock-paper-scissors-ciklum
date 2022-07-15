# Rock - Paper- Scissor

## Technology Stack
### Api
- Java 17+
- [Springboot Framework 2.7.x](https://spring.io/projects/spring-boot)

### Front
- NodeJs 16.16.0
- ES6 (via Babel 7)
- React 18.2
- CSS preprocessor: Sass

## Prerequisites
- Docker
### Api
- Java 17+

### Front

- Node v16
- Npm / Yarn

```
nvm install 16.16.0
```
```
nvm use 16.16.0
```

## How to start

### Execute tests and build
- API

UNIX OS Based
```
mvn clean test -f rock-paper-scissor-api/pom.xml
```
Windows OS Based
```
mvn clean test -f rock-paper-scissor-api\pom.xml
```

### Run apps (Vanilla version)
- Swagger will be available on http://localhost:9091/swagger-ui.html
```
mvn spring-boot:run -f rock-paper-scissor-api/pom.xml
```
- App front will be available on http://localhost:3000
```
yarn --cwd rock-paper-scissor-front start
```


### Build docker images (Know problem: CORS error in calls from fronted app to api)

- Build *rock-paper-scissor-api* image from [Dockerfile](./docker/java/Dockerfile) locally. (No need Local JDK installed)
```
docker build . -t rock-paper-scissor-api -f ./docker/java/Dockerfile
```

- Build *rock-paper-scissor-front* image from [Dockerfile](./docker/node/Dockerfile) locally. 
```
docker build . -t rock-paper-scissor-front -f ./docker/node/Dockerfile
```

### Run docker images

- *rock-paper-scissor-api* container in background (exposing port 9091).
- *rock-paper-scissor-front* container in background (exposing port 9090).
- It used the environment variables set in the [local.env](docker/node/local.env) file
- Swagger will be available on http://localhost:9091/swagger-ui.html
- App front will be available on http://localhost:9090
```
docker run -d --name rock-paper-scissor-api -p 9091:8080 --env-file ./docker/java/local.env rock-paper-scissor-api
docker run -d --name rock-paper-scissor-front -p 9090:3000 --env-file ./docker/node/local.env rock-paper-scissor-front
```


### Stop the running container
```
docker stop rock-paper-scissor-api
docker stop rock-paper-scissor-front
```