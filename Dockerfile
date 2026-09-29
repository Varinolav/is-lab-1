FROM node:22-alpine AS frontend
WORKDIR /frontend
COPY frontend/package*.json ./
RUN npm ci --no-audit --no-fund
COPY frontend ./
RUN npm run build

FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY pom.xml ./
RUN mvn --batch-mode dependency:go-offline

COPY src ./src
COPY --from=frontend /frontend/dist/ ./src/main/webapp/
RUN mvn --batch-mode --no-transfer-progress -DskipTests package \
    && mv target/*.war target/ROOT.war

FROM quay.io/wildfly/wildfly:36.0.0.Final-jdk17
COPY --from=build /root/.m2/repository/org/postgresql/postgresql/42.7.8/postgresql-42.7.8.jar /opt/jboss/wildfly/modules/org/postgresql/main/postgresql.jar
COPY docker/wildfly/postgresql-module.xml /opt/jboss/wildfly/modules/org/postgresql/main/module.xml
COPY docker/wildfly/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
COPY --from=build /workspace/target/ROOT.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
