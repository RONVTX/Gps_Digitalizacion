package org.example;

import org.example.GPSData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

    private static ArrayList<GPSData> datosGPS = new ArrayList<>();

    public static void cargarDatos() {
        String fileName = "gps_data.csv";
        datosGPS.clear(); // limpiar por si se recarga

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linea = reader.readLine(); // saltar encabezado

            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");

                if (campos.length == 5) {
                    String busId = campos[0];
                    String timestamp = campos[1];
                    double latitude = Double.parseDouble(campos[2]);
                    double longitude = Double.parseDouble(campos[3]);
                    double speed = Double.parseDouble(campos[4]);

                    GPSData data = new GPSData(busId, timestamp, latitude, longitude, speed);
                    datosGPS.add(data);
                }
            }

            System.out.println("Se cargaron " + datosGPS.size() + " registros desde " + fileName);

        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public static ArrayList<GPSData> getDatosGPS() {
        return datosGPS;
    }
}

