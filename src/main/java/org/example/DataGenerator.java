package org.example;



import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DataGenerator {

    public static void generarDatos() {
        String[] buses = {"BUS01", "BUS02", "BUS03"};
        String fileName = "gps_data.csv";
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");

            for (String busId : buses) {
                LocalDateTime currentTime = LocalDateTime.of(2025, 3, 25, 8, 0);

                for (int i = 0; i < 60; i++) { // 60 minutos
                    double latitude = 40.4000 + random.nextDouble() * 0.05;
                    double longitude = -3.7000 - random.nextDouble() * 0.05;
                    double speed = random.nextDouble() * 60;

                    GPSData data = new GPSData(busId, currentTime.format(formatter), latitude, longitude, speed);
                    writer.write(data.toCSV() + "\n");

                    currentTime = currentTime.plusMinutes(1);
                }
            }

            System.out.println("Datos GPS generados y guardados en " + fileName);

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }
}
