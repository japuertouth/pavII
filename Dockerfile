# --- Etapa 1: compilar con Maven ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -Dmaven.compiler.release=21 package dependency:copy-dependencies -DskipTests

# --- Etapa 2: ejecutar ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/classes ./classes
COPY --from=build /app/target/dependency ./lib
EXPOSE 8080
CMD ["java", "-cp", "classes:lib/*", "com.mycompany.holamundo_3periodo.WebServer"]
