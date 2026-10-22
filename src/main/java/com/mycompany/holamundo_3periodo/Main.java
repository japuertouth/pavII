package com.mycompany.holamundo_3periodo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author user
 */
import com.mycompany.holamundo_3periodo.LibroDAO;
import com.mycompany.holamundo_3periodo.Libro;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibroDAO libroDAO = new LibroDAO();

    public static void main(String[] args) {
        libroDAO.inicializarTabla();
        
        int opcion = 0;
        do {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
                switch (opcion) {
                    case 1 -> registrarLibro();
                    case 2 -> listarLibros();
                    case 3 -> actualizarLibro();
                    case 4 -> eliminarLibro();
                    case 5 -> System.out.println("\n[✓] Saliendo del sistema...");
                    default -> System.out.println("[!] Opción no válida. Ingrese un número entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Entrada inválida. Debe ingresar un número entero.");
            }
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n========================================");
        System.out.println("   GESTIÓN DE BIBLIOTECA (SQL SERVER)");
        System.out.println("========================================");
        System.out.println("1. Registrar nuevo libro");
        System.out.println("2. Consultar catálogo de libros");
        System.out.println("3. Actualizar un libro");
        System.out.println("4. Eliminar un libro");
        System.out.println("5. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    private static void registrarLibro() {
        System.out.println("\n--- REGISTRAR LIBRO ---");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Autor: ");
        String autor = scanner.nextLine().trim();
        System.out.print("Editorial: ");
        String editorial = scanner.nextLine().trim();

        if (titulo.isEmpty() || autor.isEmpty() || editorial.isEmpty()) {
            System.out.println("[!] Todos los campos son obligatorios.");
            return;
        }

        if (libroDAO.registrar(new Libro(titulo, autor, editorial))) {
            System.out.println("[✓] Libro registrado correctamente en SQL Server.");
        }
    }

    private static void listarLibros() {
        System.out.println("\n--- CATÁLOGO DE LIBROS ---");
        List<Libro> libros = libroDAO.listar();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        System.out.printf("%-5s | %-30s | %-25s | %-20s%n", "ID", "Título", "Autor", "Editorial");
        System.out.println("-".repeat(88));
        for (Libro l : libros) {
            System.out.printf("%-5d | %-30s | %-25s | %-20s%n", l.getId(), l.getTitulo(), l.getAutor(), l.getEditorial());
        }
    }

    private static void actualizarLibro() {
        listarLibros();
        System.out.println("\n--- ACTUALIZAR LIBRO ---");
        System.out.print("Ingrese el ID del libro a modificar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nuevo Título: ");
            String titulo = scanner.nextLine().trim();
            System.out.print("Nuevo Autor: ");
            String autor = scanner.nextLine().trim();
            System.out.print("Nueva Editorial: ");
            String editorial = scanner.nextLine().trim();

            if (libroDAO.actualizar(new Libro(id, titulo, autor, editorial))) {
                System.out.println("[✓] Libro actualizado exitosamente.");
            } else {
                System.out.println("[!] No se encontró ningún libro con el ID especificado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] ID no válido.");
        }
    }

    private static void eliminarLibro() {
        listarLibros();
        System.out.println("\n--- ELIMINAR LIBRO ---");
        System.out.print("Ingrese el ID del libro a eliminar: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (libroDAO.eliminar(id)) {
                System.out.println("[✓] Libro eliminado exitosamente de la base de datos.");
            } else {
                System.out.println("[!] No se encontró ningún libro con ese ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] ID no válido.");
        }
    }
}
