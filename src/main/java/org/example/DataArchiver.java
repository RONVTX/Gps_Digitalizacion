package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DataArchiver {

    private static final String ARCHIVE_DIR = "archivados";

    public static void archivarDatos() {
        File directorio = new File(ARCHIVE_DIR);
        if (!directorio.exists()) {
            directorio.mkdir(); // Crear carpeta de archivados si no existe
        }

        // Obtener todos los archivos .csv en el directorio actual
        File[] archivos = new File(".").listFiles((dir, name) -> name.endsWith(".csv"));

        if (archivos != null && archivos.length > 0) {
            for (File archivo : archivos) {
                try {
                    // Definir fecha de corte
                    LocalDate fechaActual = LocalDate.now();
                    LocalDate fechaArchivo = obtenerFechaArchivo(archivo.getName());

                    // Si el archivo tiene más de 7 días de antigüedad, archivarlo
                    if (fechaArchivo != null && fechaArchivo.isBefore(fechaActual.minusDays(7))) {
                        Path destino = Paths.get(ARCHIVE_DIR, archivo.getName());
                        Files.move(archivo.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Archivo archivado: " + archivo.getName());
                    }
                } catch (IOException e) {
                    System.err.println("Error al mover el archivo: " + e.getMessage());
                }
            }
        }
    }

    public static void eliminarArchivosAntiguos() {
        File directorio = new File(ARCHIVE_DIR);
        if (!directorio.exists()) {
            System.out.println("No hay archivos archivados.");
            return;
        }

        // Obtener archivos archivados
        File[] archivos = directorio.listFiles((dir, name) -> name.endsWith(".csv"));

        if (archivos != null && archivos.length > 0) {
            for (File archivo : archivos) {
                try {
                    LocalDate fechaArchivo = obtenerFechaArchivo(archivo.getName());

                    if (fechaArchivo != null && fechaArchivo.isBefore(LocalDate.now().minusDays(7))) {
                        boolean eliminado = archivo.delete();
                        if (eliminado) {
                            System.out.println("Archivo eliminado: " + archivo.getName());
                        } else {
                            System.out.println("No se pudo eliminar el archivo: " + archivo.getName());
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error al eliminar el archivo: " + e.getMessage());
                }
            }
        }
    }

    // Obtener fecha a partir del nombre del archivo (ej. gps_data_2025-03-25.csv)
    private static LocalDate obtenerFechaArchivo(String nombreArchivo) {
        try {
            String[] partes = nombreArchivo.split("_");
            if (partes.length >= 2) {
                String fechaStr = partes[1].replace(".csv", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(fechaStr, formatter);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener fecha de archivo: " + e.getMessage());
        }
        return null;
    }
}
