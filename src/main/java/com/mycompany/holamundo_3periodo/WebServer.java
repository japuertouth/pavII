package com.mycompany.holamundo_3periodo;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/** Interfaz web del CRUD de libros (para desplegar en Vercel/Render). Usa LibroDAO. */
public class WebServer {
    private static final LibroDAO dao = new LibroDAO();

    public static void main(String[] args) throws IOException {
        int puerto = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        dao.inicializarTabla();
        HttpServer server = HttpServer.create(new InetSocketAddress(puerto), 0);
        server.createContext("/", ex -> {
            if (ex.getRequestMethod().equals("POST")) accion(ex);
            else responder(ex, pagina());
        });
        server.start();
        System.out.println("Servidor iniciado en el puerto " + puerto);
    }

    private static void accion(HttpExchange ex) throws IOException {
        Map<String, String> f = form(ex);
        String ruta = ex.getRequestURI().getPath();
        try {
            if (ruta.equals("/agregar")) {
                dao.registrar(new Libro(f.get("titulo"), f.get("autor"), f.get("editorial")));
            } else if (ruta.equals("/actualizar")) {
                dao.actualizar(new Libro(Integer.parseInt(f.get("id")), f.get("titulo"), f.get("autor"), f.get("editorial")));
            } else if (ruta.equals("/eliminar")) {
                dao.eliminar(Integer.parseInt(f.get("id")));
            }
        } catch (NumberFormatException e) {
            System.err.println("ID no válido");
        }
        ex.getResponseHeaders().set("Location", "/");
        ex.sendResponseHeaders(303, -1);
        ex.close();
    }

    private static Map<String, String> form(HttpExchange ex) throws IOException {
        String cuerpo = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> m = new HashMap<>();
        for (String par : cuerpo.split("&")) {
            String[] kv = par.split("=", 2);
            if (kv.length == 2) m.put(kv[0], URLDecoder.decode(kv[1], StandardCharsets.UTF_8).trim());
        }
        return m;
    }

    private static String esc(String s) {
        return s == null ? "" : s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private static String pagina() {
        StringBuilder h = new StringBuilder("""
            <!doctype html><html lang="es"><head><meta charset="utf-8">
            <meta name="viewport" content="width=device-width,initial-scale=1">
            <title>Gestión de Biblioteca</title>
            <style>body{font-family:system-ui;max-width:900px;margin:2rem auto;padding:0 1rem}
            table{width:100%;border-collapse:collapse}td,th{border-bottom:1px solid #ddd;padding:6px;text-align:left}
            input{padding:5px;margin:2px}button{padding:5px 10px;cursor:pointer}
            form.fila{display:contents}</style></head><body>
            <h1>Gestión de Biblioteca</h1>
            <h2>Registrar libro</h2>
            <form method="post" action="/agregar">
            <input name="titulo" placeholder="Título" required>
            <input name="autor" placeholder="Autor" required>
            <input name="editorial" placeholder="Editorial" required>
            <button>Registrar</button></form>
            <h2>Catálogo</h2>
            <table><tr><th>ID</th><th>Título</th><th>Autor</th><th>Editorial</th><th></th></tr>
            """);
        for (Libro l : dao.listar()) {
            h.append("<tr><form method=\"post\" action=\"/actualizar\"><td>").append(l.getId())
             .append("<input type=\"hidden\" name=\"id\" value=\"").append(l.getId()).append("\"></td>")
             .append("<td><input name=\"titulo\" value=\"").append(esc(l.getTitulo())).append("\" required></td>")
             .append("<td><input name=\"autor\" value=\"").append(esc(l.getAutor())).append("\" required></td>")
             .append("<td><input name=\"editorial\" value=\"").append(esc(l.getEditorial())).append("\" required></td>")
             .append("<td><button>Guardar</button></form>")
             .append("<form method=\"post\" action=\"/eliminar\" style=\"display:inline\">")
             .append("<input type=\"hidden\" name=\"id\" value=\"").append(l.getId()).append("\">")
             .append("<button>Eliminar</button></form></td></tr>");
        }
        return h.append("</table></body></html>").toString();
    }

    private static void responder(HttpExchange ex, String html) throws IOException {
        byte[] b = html.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/html; charset=utf-8");
        ex.sendResponseHeaders(200, b.length);
        ex.getResponseBody().write(b);
        ex.close();
    }
}
