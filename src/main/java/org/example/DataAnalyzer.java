package org.example;



import org.example.GPSData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DataAnalyzer {

    public static void analizar() {
        ArrayList<GPSData> datos = DataLoader.getDatosGPS();

        if (datos.isEmpty()) {
            System.out.println("No hay datos cargados. Use la opción 2 primero.");
            return;
        }

        // Agrupar por busId
        Map<String, ArrayList<GPSData>> datosPorBus = new HashMap<>();
        for (GPSData d : datos) {
            datosPorBus.computeIfAbsent(d.getBusId(), k -> new ArrayList<>()).add(d);
        }

        System.out.println("\n--- Análisis por Autobús ---");
        for (String busId : datosPorBus.keySet()) {
            ArrayList<GPSData> lista = datosPorBus.get(busId);

            double sumaVelocidades = 0;
            int paradas = 0;

            for (GPSData d : lista) {
                sumaVelocidades += d.getSpeed();
                if (d.getSpeed() == 0) paradas++;
            }

            double velocidadMedia = sumaVelocidades / lista.size();
            System.out.printf("Bus %s -> Velocidad media: %.2f km/h | Paradas: %d\n",
                    busId, velocidadMedia, paradas);
        }
    }
}
