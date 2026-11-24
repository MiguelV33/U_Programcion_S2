package CinaColombia;
import java.util.Scanner;

public class Main {

    static final int MAX_ITEMS = 10;

    static final int FILAS_CINE = 5;
    static final int COLS_CINE = 6;

    static final int FILAS_AVION = 6;
    static final int COLS_AVION = 4;

    static final String[] PELICULAS_BASE = {"Spiderman", "Doom", "Duna"};
    static final String[] DESTINOS_BASE = {"Los Ángeles", "Berlín", "Madrid"};

    
    static String[] destinos = new String[MAX_ITEMS];
    static String[] codigosVuelo = new String[MAX_ITEMS];
    static double[] preciosVuelo = new double[MAX_ITEMS];
    static char[][][] asientosAvion = new char[MAX_ITEMS][FILAS_AVION][COLS_AVION];
    static String[][][] pasajerosAvion = new String[MAX_ITEMS][FILAS_AVION][COLS_AVION];
    static int totalVuelos = 0;

    public static void main(String[] args) {
        cargarCatalogosPredeterminados();
        Scanner sc = new Scanner(System.in);
        int opcionPrincipal;

        do {
            System.out.println("\nEscoja lo que quiere hacer");
            System.out.println("1. Módulo Cine Colombia");
            System.out.println("2. Módulo Avianca");
            System.out.println("0. Salir del sistema");
            opcionPrincipal = leerEnteroEnRango(sc, "Seleccione una opción: ", 0, 2);

            switch (opcionPrincipal) {
                case 1:
                    menuCineColombia(sc);
                    break;
                case 2:
                    menuAvianca(sc);
                    break;
                case 0:
                    System.out.println("\nCerrando sistema.");
                    break;
            }
        } while (opcionPrincipal != 0);

        sc.close();
    }

    public static void cargarCatalogosPredeterminados() {
        agregarFuncionInterna("Spiderman", "16:00", 18000);
        agregarFuncionInterna("Doom", "19:00", 20000);
        agregarFuncionInterna("Duna", "21:30", 22000);

        agregarVueloInterno("001", "Los Ángeles", 1850000);
        agregarVueloInterno("002", "Berlín", 3200000);
        agregarVueloInterno("003", "Madrid", 2950000);
    }

    public static void agregarFuncionInterna(String pelicula, String horario, double precio) {
        peliculas[totalFunciones] = pelicula;
        horariosCine[totalFunciones] = horario;
        preciosCine[totalFunciones] = precio;
        inicializarMatrizEstado(salasCine[totalFunciones]);
        totalFunciones++;
    }

    public static void agregarVueloInterno(String codigo, String destino, double precio) {
        codigosVuelo[totalVuelos] = codigo;
        destinos[totalVuelos] = destino;
        preciosVuelo[totalVuelos] = precio;
        inicializarMatrizEstado(asientosAvion[totalVuelos]);
        for (int f = 0; f < FILAS_AVION; f++) {
            for (int c = 0; c < COLS_AVION; c++) {
                pasajerosAvion[totalVuelos][f][c] = "";
            }
        }
        totalVuelos++;
    }

    public static void menuCineColombia(Scanner sc) {
        int opcion;
        do {
            System.out.println("\nMÓDULO CINE COLOMBIA");
            System.out.println("1. Registrar nuevo horario para película");
            System.out.println("2. Consultar cartelera y horarios disponibles");
            System.out.println("3. Seleccionar película y reservar silla");
            System.out.println("4. Consultar disponibilidad de sillas por función");
            System.out.println("5. Calcular valor total de reservas realizadas");
            System.out.println("0. Volver al menú principal");
            opcion = leerEnteroEnRango(sc, "Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1:
                    registrarFuncionCine(sc);
                    break;
                case 2:
                    consultarCarteleraCine();
                    break;
                case 3:
                    reservarSillaCine(sc);
                    break;
                case 4:
                    consultarDisponibilidadCine(sc);
                    break;
                case 5:
                    calcularIngresosCine();
                    break;
                case 0:
                    System.out.println("Regresando al menú principal.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void registrarFuncionCine(Scanner sc) {
        if (totalFunciones >= MAX_ITEMS) {
            System.out.println("\nCapacidad máxima de funciones alcanzada (" + MAX_ITEMS + ").");
            return;
        }

        System.out.println("\nREGISTRO DE FUNCIÓN ");
        for (int i = 0; i < PELICULAS_BASE.length; i++) {
            System.out.println((i + 1) + ". " + PELICULAS_BASE[i]);
        }
        int seleccion = leerEnteroEnRango(sc, "Seleccione la película (1-" + PELICULAS_BASE.length + "): ", 1, PELICULAS_BASE.length);
        String pelicula = PELICULAS_BASE[seleccion - 1];
        String horario = leerTextoNoVacio(sc, "Horario de la función (ej. 18:30): ");

        for (int i = 0; i < totalFunciones; i++) {
            if (peliculas[i].equalsIgnoreCase(pelicula) && horariosCine[i].equalsIgnoreCase(horario)) {
                System.out.println("\nError: Ya existe esa película registrada en el mismo horario.");
                return;
            }
        }

        double precio = leerDoublePositivo(sc, "Precio de la boleta ($ COP): ");
        agregarFuncionInterna(pelicula, horario, precio);

        System.out.println("\nFunción registrada exitosamente con sala de " + (FILAS_CINE * COLS_CINE) + " sillas libres.");
    }

    static String[] peliculas = new String[MAX_ITEMS];
    static String[] horariosCine = new String[MAX_ITEMS];
    static double[] preciosCine = new double[MAX_ITEMS];
    static char[][][] salasCine = new char[MAX_ITEMS][FILAS_CINE][COLS_CINE];
    static int totalFunciones = 0;

    
    public static void consultarCarteleraCine() {
        System.out.println("\nCARTELERA DISPONIBLE (CINE COLOMBIA)");
        System.out.printf("%-4s  %-15s  %-10s  %-12s  %-12s%n",
                "N°", "Película", "Horario", "Precio", "Disponibles");

        for (int i = 0; i < totalFunciones; i++) {
            int libres = contarCeldasPorEstado(salasCine[i], 'L');
            int totalSillas = FILAS_CINE * COLS_CINE;
            System.out.printf("%-4d  %-15s  %-10s  $%-11.2f  %d/%d%n",
                    (i + 1), peliculas[i], horariosCine[i], preciosCine[i], libres, totalSillas);
        }
    }

    public static void reservarSillaCine(Scanner sc) {
        consultarCarteleraCine();
        int idx = leerEnteroEnRango(sc, "\nSeleccione la opción de película (1-" + totalFunciones + "): ", 1, totalFunciones) - 1;

        int libres = contarCeldasPorEstado(salasCine[idx], 'L');
        if (libres == 0) {
            System.out.println("\nLa sala para esta función está completamente llena.");
            return;
        }

        mostrarMatrizCine(salasCine[idx], peliculas[idx], horariosCine[idx]);

        char filaMax = (char) ('A' + FILAS_CINE - 1);
        String filaStr = leerTextoNoVacio(sc, "Ingrese la letra de la fila (A-" + filaMax + "): ").toUpperCase();

        if (filaStr.length() != 1 || filaStr.charAt(0) < 'A' || filaStr.charAt(0) > filaMax) {
            System.out.println("\nError: Fila inválida. Debe estar entre A y " + filaMax + ".");
            return;
        }

        int fila = filaStr.charAt(0) - 'A';
        int col = leerEnteroEnRango(sc, "Ingrese el número de silla (1-" + COLS_CINE + "): ", 1, COLS_CINE) - 1;

        if (salasCine[idx][fila][col] == 'O') {
            System.out.println("\nReserva rechazada: La silla " + filaStr + (col + 1) + " ya está ocupada.");
            return;
        }

        salasCine[idx][fila][col] = 'O';

        System.out.println("\nReserva confirmada:");
        System.out.println("Película: " + peliculas[idx] + " (" + horariosCine[idx] + ")");
        System.out.println("Silla: " + filaStr + (col + 1));
        System.out.printf("Valor: $%.2f%n", preciosCine[idx]);

        mostrarMatrizCine(salasCine[idx], peliculas[idx], horariosCine[idx]);
    }

    public static void consultarDisponibilidadCine(Scanner sc) {
        consultarCarteleraCine();
        int idx = leerEnteroEnRango(sc, "\nSeleccione la opción de película (1-" + totalFunciones + "): ", 1, totalFunciones) - 1;

        mostrarMatrizCine(salasCine[idx], peliculas[idx], horariosCine[idx]);
        int libres = contarCeldasPorEstado(salasCine[idx], 'L');
        int ocupadas = contarCeldasPorEstado(salasCine[idx], 'O');

        System.out.println("Resumen de ocupación:");
        System.out.println("Sillas disponibles (L): " + libres);
        System.out.println("Sillas ocupadas (O): " + ocupadas);
    }

    public static void calcularIngresosCine() {
        System.out.println("\nREPORTE DE INGRESOS (CINE COLOMBIA)");
        double totalGeneral = 0;

        for (int i = 0; i < totalFunciones; i++) {
            int ocupadas = contarCeldasPorEstado(salasCine[i], 'O');
            double subtotal = ocupadas * preciosCine[i];
            totalGeneral += subtotal;
            System.out.printf("%-15s (%s)  %2d sillas vendidas  Subtotal: $%.2f%n",
                    peliculas[i], horariosCine[i], ocupadas, subtotal);
        }

        System.out.printf("Valor total de reservas realizadas: $%.2f%n", totalGeneral);
    }

    public static void menuAvianca(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n AVIANCA");
            System.out.println("1. Registrar nuevo vuelo a destino predeterminado");
            System.out.println("2. Consultar vuelos y tarifas disponibles");
            System.out.println("3. Seleccionar vuelo y reservar asiento");
            System.out.println("4. Consultar disponibilidad y pasajeros por vuelo");
            System.out.println("5. Calcular valor total de tiquetes vendidos");
            System.out.println("0. Volver al menú principal");
            opcion = leerEnteroEnRango(sc, "Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1:
                    registrarVueloAvianca(sc);
                    break;
                case 2:
                    consultarVuelosAvianca();
                    break;
                case 3:
                    reservarAsientoAvianca(sc);
                    break;
                case 4:
                    consultarDisponibilidadAvianca(sc);
                    break;
                case 5:
                    calcularIngresosAvianca();
                    break;
                case 0:
                    System.out.println("Regresando al menú principal.");
                    break;
            }
        } while (opcion != 0);
    }

    public static void registrarVueloAvianca(Scanner sc) {
        if (totalVuelos >= MAX_ITEMS) {
            System.out.println("\nCapacidad máxima de vuelos alcanzada (" + MAX_ITEMS + ").");
            return;
        }

        System.out.println("\nREGISTRO DE VUELO ");
        for (int i = 0; i < DESTINOS_BASE.length; i++) {
            System.out.println((i + 1) + ". " + DESTINOS_BASE[i]);
        }
        int seleccion = leerEnteroEnRango(sc, "Seleccione el destino (1-" + DESTINOS_BASE.length + "): ", 1, DESTINOS_BASE.length);
        String destino = DESTINOS_BASE[seleccion - 1];
        String codigo = leerTextoNoVacio(sc, "Código de vuelo ( 004): ").toUpperCase();

        for (int i = 0; i < totalVuelos; i++) {
            if (codigosVuelo[i].equalsIgnoreCase(codigo)) {
                System.out.println("\nError: El código de vuelo " + codigo + " ya está registrado.");
                return;
            }
        }

        double precio = leerDoublePositivo(sc, "Precio del tiquete : ");
        agregarVueloInterno(codigo, destino, precio);

        System.out.println("\nVuelo " + codigo + " con destino a " + destino + " registrado.");
    }

    public static void consultarVuelosAvianca() {
        System.out.println("\nVUELOS DISPONIBLES (AVIANCA)");
        System.out.printf("%-4s  %-16s  %-8s  %-14s  %-12s%n",
                "N°", "Destino", "Código", "Precio", "Disponibles");

        for (int i = 0; i < totalVuelos; i++) {
            int libres = contarCeldasPorEstado(asientosAvion[i], 'L');
            int totalAsientos = FILAS_AVION * COLS_AVION;
            System.out.printf("%-4d  %-16s  %-8s  $%-13.2f  %d/%d%n",
                    (i + 1), destinos[i], codigosVuelo[i], preciosVuelo[i], libres, totalAsientos);
        }
    }

    public static void reservarAsientoAvianca(Scanner sc) {
        consultarVuelosAvianca();
        int idx = leerEnteroEnRango(sc, "\nSeleccione la opción de vuelo (1-" + totalVuelos + "): ", 1, totalVuelos) - 1;

        if (contarCeldasPorEstado(asientosAvion[idx], 'L') == 0) {
            System.out.println("\nEl vuelo " + codigosVuelo[idx] + " no tiene asientos disponibles.");
            return;
        }

        mostrarMatrizAvion(asientosAvion[idx], codigosVuelo[idx], destinos[idx]);

        int fila = leerEnteroEnRango(sc, "Ingrese el número de fila (1-" + FILAS_AVION + "): ", 1, FILAS_AVION) - 1;
        char colMax = (char) ('A' + COLS_AVION - 1);
        String colStr = leerTextoNoVacio(sc, "Ingrese la letra del asiento (A-" + colMax + "): ").toUpperCase();

        if (colStr.length() != 1 || colStr.charAt(0) < 'A' || colStr.charAt(0) > colMax) {
            System.out.println("\nError: Letra de asiento inválida. Debe estar entre A y " + colMax + ".");
            return;
        }

        int col = colStr.charAt(0) - 'A';

        if (asientosAvion[idx][fila][col] == 'O') {
            System.out.println("\nReserva rechazada: El asiento " + (fila + 1) + colStr + " ya está ocupado.");
            return;
        }

        String nombrePasajero = leerTextoNoVacio(sc, "Nombre completo del pasajero: ");
        String docPasajero = leerTextoNoVacio(sc, "Documento de identidad del pasajero: ");

        asientosAvion[idx][fila][col] = 'O';
        pasajerosAvion[idx][fila][col] = nombrePasajero + " (Doc: " + docPasajero + ")";

        System.out.println("\nTiquete emitido exitosamente:");
        System.out.println("Vuelo: " + destinos[idx] + " " + codigosVuelo[idx]);
        System.out.println("Pasajero: " + pasajerosAvion[idx][fila][col]);
        System.out.println("Asiento: " + (fila + 1) + colStr);
        System.out.printf("Valor: $%.2f%n", preciosVuelo[idx]);

        mostrarMatrizAvion(asientosAvion[idx], codigosVuelo[idx], destinos[idx]);
    }

    public static void consultarDisponibilidadAvianca(Scanner sc) {
        consultarVuelosAvianca();
        int idx = leerEnteroEnRango(sc, "\nSeleccione la opción de vuelo (1-" + totalVuelos + "): ", 1, totalVuelos) - 1;
        mostrarMatrizAvion(asientosAvion[idx], codigosVuelo[idx], destinos[idx]);

        int libres = contarCeldasPorEstado(asientosAvion[idx], 'L');
        int ocupados = contarCeldasPorEstado(asientosAvion[idx], 'O');

        System.out.println("Resumen de ocupación:");
        System.out.println("Asientos disponibles (L): " + libres);
        System.out.println("Asientos ocupados (O): " + ocupados);

        if (ocupados > 0) {
            System.out.println("\nLista de pasajeros registrados en este vuelo:");
            for (int f = 0; f < FILAS_AVION; f++) {
                for (int c = 0; c < COLS_AVION; c++) {
                    if (asientosAvion[idx][f][c] == 'O') {
                        char letraCol = (char) ('A' + c);
                        System.out.println("Asiento " + (f + 1) + letraCol + ": " + pasajerosAvion[idx][f][c]);
                    }
                }
            }
        }
    }

    public static void calcularIngresosAvianca() {
        System.out.println("\nREPORTE DE TIQUETES VENDIDOS (AVIANCA)");
        double totalGeneral = 0;

        for (int i = 0; i < totalVuelos; i++) {
            int vendidos = contarCeldasPorEstado(asientosAvion[i], 'O');
            double subtotal = vendidos * preciosVuelo[i];
            totalGeneral += subtotal;
            System.out.printf("%-14s %-6s  %2d tiquetes vendidos  Subtotal: $%.2f%n",
                    destinos[i], codigosVuelo[i], vendidos, subtotal);
        }

        System.out.printf("Valor total de tiquetes vendidos: $%.2f%n", totalGeneral);
    }

    public static void inicializarMatrizEstado(char[][] matriz) {
        for (int f = 0; f < matriz.length; f++) {
            for (int c = 0; c < matriz[f].length; c++) {
                matriz[f][c] = 'L';
            }
        }
    }

    public static int contarCeldasPorEstado(char[][] matriz, char estado) {
        int contador = 0;
        for (int f = 0; f < matriz.length; f++) {
            for (int c = 0; c < matriz[f].length; c++) {
                if (matriz[f][c] == estado) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public static void mostrarMatrizCine(char[][] sala, String pelicula, String horario) {
        System.out.println("\nMapa de Sala - " + pelicula + " (" + horario + ")");
        System.out.println("       PANTALLA");
        System.out.print("    ");
        for (int c = 0; c < COLS_CINE; c++) {
            System.out.print((c + 1) + "  ");
        }
        System.out.println();

        for (int f = 0; f < FILAS_CINE; f++) {
            char letraFila = (char) ('A' + f);
            System.out.print(" " + letraFila + "  ");
            for (int c = 0; c < COLS_CINE; c++) {
                System.out.print(sala[f][c] + "  ");
            }
            System.out.println();
        }
        System.out.println("Leyenda: L = Libre, O = Ocupada\n");
    }

    public static void mostrarMatrizAvion(char[][] cabina, String codigo, String destino) {
        System.out.println("\nMapa de Cabina - Vuelo " + destino + " " + codigo);
        System.out.println("      A  B    C  D");
        for (int f = 0; f < FILAS_AVION; f++) {
            System.out.printf(" %2d   ", (f + 1));
            for (int c = 0; c < COLS_AVION; c++) {
                System.out.print(cabina[f][c] + "  ");
                if (c == 1) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("Leyenda: L = Libre, O = Ocupado\n");
    }

    public static String leerTextoNoVacio(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            if (!entrada.isEmpty()) {
                return entrada;
            }
            System.out.println("Este campo no puede quedar vacío.");
        }
    }

    public static int leerEnteroEnRango(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Ingrese un número entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    public static double leerDoublePositivo(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();
            try {
                double valor = Double.parseDouble(entrada);
                if (valor > 0) {
                    return valor;
                }
                System.out.println("El valor debe ser mayor que 0.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un valor numérico válido.");
            }
        }
    }
}