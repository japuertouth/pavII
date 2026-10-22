/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.holamundo_3periodo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class ConexionDB{
    private static final String URL = "jdbc:sqlserver://progav2.database.windows.net:1433;database=avanzadaii;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"; 
    private static final String USER = "japuerto@progav2";
    private static final String PASSWORD = "ProgAv226";

    private static Connection conexion = null;
    private ConexionDB(){}  
    public static Connection getConexion() {
        try{
            if(conexion == null || conexion.isClosed()){
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //Establecer la conexion
                    conexion = DriverManager.getConnection(URL,USER,PASSWORD);
                    System.out.println("->Conexion exitosa a sql");
            }
        }catch (ClassNotFoundException e){
        System.err.println("Error no se encontro el driver de mssql-jdbc" + e.getMessage());
        }catch(SQLException e){
        System.err.println("Error al conectar con SQL" + e.getMessage());
        }
        return conexion;
    }
    public static void cerrarConexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.println("->La conexion con SQL fue cerrada");
            }
        }catch(SQLException e){
            System.err.println("Error al cerrar la conexion" + e.getMessage());
        }
    }
}
