package org.example;



import org.example.GPSData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DataProcessor {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static void procesarDatos() {
        ArrayList<GPSData> datosOriginales = DataLoader.getDatosGPS();

        if (datosOriginales.isEmpty()) {
            System.out.println("No hay datos cargados. Use la opción 2 primero.");
            return;
        }

        ArrayList<GPSData> datosValidos = new ArrayList<>();

        for (GPSData data : datosOriginales) {
            if (validarRegistro(data)) {
                datosValidos.add(data);
            }
        }

        System.out.println("Registros válidos encontrados: " + datosValidos.size());
        // Podrías almacenar estos en memoria si se desea mantener separados los válidos

        // Filtrado opcional por bus y horario
        System.out.println("¿Desea filtrar por busId y rango horario? (s/n)");
        String respuesta = new java.util.Scanner(System.in).nextLine();

        if (respuesta.equalsIgnoreCase("s")) {
            java.util.Scanner sc = new java.util.Scanner(System.in);
            System.out.print("Ingrese busId (ej. BUS01): ");
            String busId = sc.nextLine();
            System.out.print("Hora inicial (ej. 2025-03-25T08:10:00): ");
            String inicioStr = sc.nextLine();
            System.out.print("Hora final (ej. 2025-03-25T08:40:00): ");
            String finStr = sc.nextLine();

            LocalDateTime inicio = LocalDateTime.parse(inicioStr, formatter);
            LocalDateTime fin = LocalDateTime.parse(finStr, formatter);

            ArrayList<GPSData> filtrados = datosValidos.stream()
                    .filter(d -> d.getBusId().equalsIgnoreCase(busId))
                    .filter(d -> {
                        LocalDateTime t = LocalDateTime.parse(d.getTimestamp(), formatter);
                        return (t.isEqual(inicio) || t.isAfter(inicio)) && (t.isEqual(fin) || t.isBefore(fin));
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

            System.out.println("Registros filtrados: " + filtrados.size());
            // Aquí puedes guardar esta lista en otro lado si la quieres usar luego
        }
    }

    private static boolean validarRegistro(GPSData data) {
        try {
            double lat = data.getLatitude();
            double lon = data.getLongitude();
            double spd = data.getSpeed();
            LocalDateTime.parse(data.getTimestamp(), formatter); // valida formato timestamp

            return (lat >= -90 && lat <= 90) &&
                    (lon >= -180 && lon <= 180) &&
                    (spd >= 0 && spd <= 120); // límite razonable de velocidad

        } catch (Exception e) {
            return false;
        }
    }
}

