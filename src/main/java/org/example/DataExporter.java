package org.example;



import org.example.GPSData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataExporter {

    public static void exportarUltimaPosicion() {
        ArrayList<GPSData> datos = DataLoader.getDatosGPS();

        if (datos.isEmpty()) {
            System.out.println("No hay datos cargados. Use la opción 2 primero.");
            return;
        }

        // Mapa para guardar la última posición por bus
        Map<String, GPSData> ultimasPosiciones = new HashMap<>();

        for (GPSData d : datos) {
            // Siempre reemplaza porque los datos están ordenados por tiempo
            ultimasPosiciones.put(d.getBusId(), d);
        }

        for (String busId : ultimasPosiciones.keySet()) {
            GPSData data = ultimasPosiciones.get(busId);
            String fileName = busId.toLowerCase() + "_status.json";

            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write("{\n");
                writer.write("  \"busId\": \"" + data.getBusId() + "\",\n");
                writer.write("  \"latitude\": " + data.getLatitude() + ",\n");
                writer.write("  \"longitude\": " + data.getLongitude() + ",\n");
                writer.write("  \"timestamp\": \"" + data.getTimestamp() + "\"\n");
                writer.write("}\n");

                System.out.println("Última posición exportada a: " + fileName);
            } catch (IOException e) {
                System.err.println("Error al escribir archivo JSON: " + e.getMessage());
            }
        }
    }
}
