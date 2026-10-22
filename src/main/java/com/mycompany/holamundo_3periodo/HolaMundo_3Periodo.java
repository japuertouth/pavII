/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.holamundo_3periodo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 *
 * @author user
 */

//agregar: actualizar y eliminar y validar que el usuario no exista
public class HolaMundo_3Periodo {

    public static void main(String[] args) throws SQLException {
        System.out.println("===Ejecutando operaciones en tabla Alumnos ===");
        //obtener la conexion
        Connection cn = ConexionDB.getConexion();
            if(cn == null){
            System.out.println("No se puede establecer la conexion");
            return;
            }
            //Declarar los objetos de SQL para nuestra base
            PreparedStatement psInsertar = null;
            PreparedStatement psSeleccionar = null;
            PreparedStatement psEliminar = null;
            PreparedStatement psActualizar = null;
            ResultSet rs = null;
            try{
                System.out.println("\n 1. Intentando insertar nuevo alumno...");
                String psInsert = "INSERT INTO Alumnos(nombre, correo)" + 
                        "SELECT ?,? WHERE NOT EXISTS (SELECT 1 FROM Alumnos WHERE correo = ?)";
                psInsertar = cn.prepareStatement(psInsert);
                
                //asignaremos valores a nuestros parametros
                psInsertar.setString(1, "Josue Puerto");
                psInsertar.setString(2, "j.puerto@uth.hn");
                psInsertar.setString(3, "j.puerto@uth.hn");
                //ejecutar la insercion
                int filasAfectadas = psInsertar.executeUpdate();
                if (filasAfectadas > 0){
                System.out.println("!Alumno insertado con exito!");
                }else{
                    System.out.println("El alumno ya existe");
                }
                //Actualizar alumno
                System.out.println("\n 2. Intentando actualizar el alumno");
                int idActualizar = 8;
                String sqlUpdate = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
                psActualizar = cn.prepareStatement(sqlUpdate);
                
                psActualizar.setString(1, "Camilo Sesto");
                psActualizar.setString(2, "camilo.sesto@uth.hn");
                psActualizar.setInt(3, idActualizar);
                
                int filasActualizadas = psActualizar.executeUpdate();
                if (filasActualizadas > 0){
                    System.out.println("Alumno actualizado correctamente");
                }else{
                    System.out.println("No se encontro ningun alumno con el id ingresado");
                }
                
                //Eliminar alumno
                System.out.println("\n 3. Intentando eliminar el alumno");
                int idEliminar = 2;
                String sqlDelete = "DELETE FROM Alumnos WHERE id = ?";
                psEliminar = cn.prepareStatement(sqlDelete);
                
                psEliminar.setInt(1, idEliminar);
                
                int filasEliminadas = psEliminar.executeUpdate();
                if (filasEliminadas > 0){
                    System.out.println("Alumno eliminado correctamente");
                }else{
                    System.out.println("No se encontro ningun alumno con el id ingresado");
                }
                
                //Parte B Seleccionar y visualizar datos en pantalla
                System.out.println("\n4. Consulta de Lista de Alumnos en la Base de Datos");
                String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
                psSeleccionar = cn.prepareStatement(sqlSelect);
                rs = psSeleccionar.executeQuery();
                System.out.println("\n ======================================");
                System.out.println("ID  | Nombre                | Correo    ");
                System.out.println("=========================================");
                while(rs.next()){
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String correo = rs.getString("correo");
                        //Imprimimos la tabla virtual
                        System.out.printf("%-3d | %-20s | %s\n", id, nombre, correo);
                }
                System.out.println("===========================");
            }catch(SQLException e){
                System.err.println("Ocurrio un error con la base de datos" + e.getMessage());
            }finally{
                //cerrar la conexion de manera obligatoria
                if(rs != null){
                    rs.close();
                }
                if(psInsertar != null)psInsertar.close();
                if(psSeleccionar != null)psSeleccionar.close();
                if(psEliminar != null)psEliminar.close();
                if(psActualizar != null)psActualizar.close();
                ConexionDB.cerrarConexion();
            }
        }
    }
