# itsjob
A project to control applicant and position

Swagger Documentation
http://localhost:9090/istjob/swagger-ui/index.html

OpenAPI Docs
http://localhost:9090/istjob/api-docs

#Comandos para subir com docker
Na pasta do projeto rode:
docker build -t ist-job:1.0 .
docker run -d --name db_istjob -e POSTGRES_PASSWORD=postgres -p 5432:5432 postgres:latest
docker run -d --name ist-job -p 9090:9090 --link meu-postgres ist-job:1.0
