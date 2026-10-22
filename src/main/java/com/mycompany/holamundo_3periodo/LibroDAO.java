package com.mycompany.holamundo_3periodo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import com.mycompany.holamundo_3periodo.Conexion;
import com.mycompany.holamundo_3periodo.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public void inicializarTabla() {
        String sql = """
            IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'libros')
            BEGIN
                CREATE TABLE libros (
                    id INT IDENTITY(1,1) PRIMARY KEY,
                    titulo VARCHAR(150) NOT NULL,
                    autor VARCHAR(100) NOT NULL,
                    editorial VARCHAR(100) NOT NULL
                );
            END
        """;
        try (Connection con = Conexion.getConexion();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("[!] Error al verificar/crear la tabla: " + e.getMessage());
        }
    }

    public boolean registrar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, editorial) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, autor, editorial FROM libros";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editorial")
                ));
            }
        } catch (SQLException e) {
            System.err.println("[!] Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    public boolean actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, editorial = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }
}
