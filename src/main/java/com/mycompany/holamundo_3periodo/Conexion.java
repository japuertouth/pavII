package com.mycompany.holamundo_3periodo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexión a SQL Server. Los datos se leen de variables de entorno
 * (DB_URL, DB_USER, DB_PASSWORD) para no guardar credenciales en el código.
 */
public class Conexion {
    private static final String URL = env("DB_URL",
            "jdbc:sqlserver://localhost:1433;databaseName=AvanzadaII;encrypt=true;trustServerCertificate=true;");
    private static final String USUARIO = env("DB_USER", "sa");
    private static final String PASSWORD = env("DB_PASSWORD", "");

    private static String env(String clave, String porDefecto) {
        String v = System.getenv(clave);
        return (v == null || v.isBlank()) ? porDefecto : v;
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
