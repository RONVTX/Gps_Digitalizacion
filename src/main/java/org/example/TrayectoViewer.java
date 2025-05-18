package org.example;



import org.example.GPSData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class TrayectoViewer {

    public static void mostrarTrayectos() {
        ArrayList<GPSData> datos = DataLoader.getDatosGPS();

        if (datos.isEmpty()) {
            System.out.println("No hay datos cargados. Use la opción 2 primero.");
            return;
        }

        // Agrupar por busId
        HashMap<String, ArrayList<GPSData>> datosPorBus = new HashMap<>();

        for (GPSData d : datos) {
            datosPorBus.computeIfAbsent(d.getBusId(), k -> new ArrayList<>()).add(d);
        }

        System.out.println("\n--- Trayecto de cada autobús ---");

        for (String busId : datosPorBus.keySet()) {
            ArrayList<GPSData> trayecto = datosPorBus.get(busId);

            // Ordenar por timestamp
            trayecto.sort(Comparator.comparing(GPSData::getTimestamp));

            System.out.println("\nAutobús: " + busId);
            System.out.println("Timestamp\t\tLat\t\tLong\t\tSpeed");

            for (GPSData d : trayecto) {
                System.out.printf("%s\t%.5f\t%.5f\t%.1f km/h\n",
                        d.getTimestamp(), d.getLatitude(), d.getLongitude(), d.getSpeed());
            }
        }
    }
}

