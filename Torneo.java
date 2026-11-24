package CinaColombia;

public class Torneo {
    public static void main(String[] args) {
        int[][] p = {
            {10, 20, 5},
            {15, 0, 30},
            {8, 8, 8},
            {25, 5, 12}
        };

        int mejorSuma = -1, mejorJugador = -1;

        for (int i = 0; i < p.length; i++) {
            int suma = 0;
            for (int j = 0; j < p[i].length; j++) {
                suma += p[i][j];
            }
            System.out.println("Jugador " + i + ": " + suma);
            
            if (suma > mejorSuma) {
                mejorSuma = suma;
                mejorJugador = i;
            }
        }

        for (int j = 0; j < p[0].length; j++) {
            int suma = 0;
            for (int i = 0; i < p.length; i++) {
                suma += p[i][j];
            }
            System.out.println("Ronda " + j + ": " + suma);
        }

        System.out.println("Ganador: jugador " + mejorJugador + " con " + mejorSuma);
    }
}