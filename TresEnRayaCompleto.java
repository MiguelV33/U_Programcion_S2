package CinaColombia;

import java.util.Scanner;

public class TresEnRayaCompleto {

    static void imprimir(char[][] t) {
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + t[i][0] + " | " + t[i][1] + " | " + t[i][2]);
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
    }

    static boolean gano(char[][] t, char p) {
        for (int i = 0; i < 3; i++) {
            if (t[i][0] == p && t[i][1] == p && t[i][2] == p) return true;
            if (t[0][i] == p && t[1][i] == p && t[2][i] == p) return true;
        }
        if (t[0][0] == p && t[1][1] == p && t[2][2] == p) return true;
        if (t[0][2] == p && t[1][1] == p && t[2][0] == p) return true;
        
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[][] t = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                t[i][j] = ' ';
            }
        }

        char turno = 'X';
        int jugadas = 0;

        while (jugadas < 9) {
            imprimir(t);
            System.out.print("Turno de " + turno + " (fila columna, de 0 a 2): ");
            int f = sc.nextInt();
            int c = sc.nextInt();

            if (f < 0 || f > 2 || c < 0 || c > 2) {
                System.out.println("Fuera del tablero.");
                continue;
            }

            if (t[f][c] != ' ') {
                System.out.println("Casilla ocupada.");
                continue;
            }

            t[f][c] = turno;
            jugadas++;

            if (gano(t, turno)) {
                imprimir(t);
                System.out.println("Gana " + turno + "!");
                sc.close();
                return;
            }

            turno = (turno == 'X') ? 'O' : 'X';
        }

        imprimir(t);
        System.out.println("Empate.");
        sc.close();
    }
}