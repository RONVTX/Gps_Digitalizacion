package org.example;



import org.example.GPSData;

import java.util.ArrayList;
import java.util.Scanner;

public class DataUpdater {

    public static void actualizarRecorrido() {
        ArrayList<GPSData> datos = DataLoader.getDatosGPS();

        if (datos.isEmpty()) {
            System.out.println("No hay datos cargados. Use la opción 2 primero.");
            return;
        }

        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese el busId que desea actualizar (ej. BUS01): ");
        String busId = sc.nextLine();

        System.out.print("Ingrese el timestamp del registro a modificar (ej. 2025-03-25T08:10:00): ");
        String timestamp = sc.nextLine();

        // Buscar el registro a modificar
        boolean encontrado = false;
        for (GPSData d : datos) {
            if (d.getBusId().equalsIgnoreCase(busId) && d.getTimestamp().equals(timestamp)) {
                // Solicitar nuevos valores para modificar
                System.out.print("Ingrese la nueva latitud: ");
                double nuevaLatitud = sc.nextDouble();
                System.out.print("Ingrese la nueva longitud: ");
                double nuevaLongitud = sc.nextDouble();
                System.out.print("Ingrese la nueva velocidad: ");
                double nuevaVelocidad = sc.nextDouble();

                // Actualizar el registro
                d = new GPSData(busId, timestamp, nuevaLatitud, nuevaLongitud, nuevaVelocidad);
                encontrado = true;
                System.out.println("Registro actualizado: " + d.toCSV());
                break;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontró el registro especificado.");
        }
    }
}
