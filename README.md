# HolaMundo_3Periodo - Gestión de Biblioteca

Aplicación Java (Maven) con CRUD de libros sobre SQL Server / Azure SQL Database.
Incluye una interfaz web (`WebServer`) y un menú de consola (`Main`).

## Variables de entorno
| Variable | Descripción |
|----------|-------------|
| `DB_URL` | Cadena JDBC de la base de datos |
| `DB_USER` | Usuario |
| `DB_PASSWORD` | Contraseña |

Las credenciales no se guardan en el código.

## Ejecutar en consola
```bash
mvn compile exec:java -Dexec.mainClass=com.mycompany.holamundo_3periodo.Main
```

## Ejecutar la interfaz web en local
```bash
mvn compile exec:java -Dexec.mainClass=com.mycompany.holamundo_3periodo.WebServer
```
Abrir http://localhost:8080

## Despliegue
`Dockerfile.vercel` en la raíz permite desplegar en Vercel (runtime de contenedores, Beta).
