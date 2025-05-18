package org.example;

import java.util.Scanner;

public class SistemaGPSMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- SISTEMA INTEGRAL DE SEGUIMIENTO GPS EN AUTOBUSES ---");
            System.out.println("1. Generar datos GPS simulados");
            System.out.println("2. Cargar y mostrar datos desde CSV");
            System.out.println("3. Procesar datos (validar, filtrar)");
            System.out.println("4. Analizar datos (velocidad media, paradas)");
            System.out.println("5. Exportar última posición en JSON");
            System.out.println("6. Visualizar trayecto de autobús");
            System.out.println("7. Modificar registros (cambio de ruta)");
            System.out.println("8. Archivar/eliminar datos antiguos");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    DataGenerator.generarDatos();
                    break;
                case 2:
                    DataLoader.cargarDatos();
                    break;
                case 3:
                    DataProcessor.procesarDatos();
                    break;
                case 4:
                    DataAnalyzer.analizar();
                    break;
                case 5:
                    DataExporter.exportarUltimaPosicion();
                    break;
                case 6:
                    TrayectoViewer.mostrarTrayectos();
                    break;
                case 7:
                    DataUpdater.actualizarRecorrido();
                    break;
                case 8:
                    DataArchiver.archivarDatos();
                    DataArchiver.eliminarArchivosAntiguos();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        scanner.close();
    }
}
