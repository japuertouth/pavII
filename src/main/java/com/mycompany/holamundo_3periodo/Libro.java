/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.holamundo_3periodo;

/**
 *
 * @author user
 */
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String editorial;

    public Libro() {}

    public Libro(String titulo, String autor, String editorial) {
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
    }

    public Libro(int id, String titulo, String autor, String editorial) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
}
