package CinaColombia;

public class Tesoros {
    public static void main(String[] args) {
        char[][] mapa = new char[6][6];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                mapa[i][j] = '.';
            }
        }

        int colocados = 0;
        while (colocados < 5) {
            int f = (int) (Math.random() * 6);
            int c = (int) (Math.random() * 6);
            
            if (mapa[f][c] != 'T') {
                mapa[f][c] = 'T';
                colocados++;
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }
}