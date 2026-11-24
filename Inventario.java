package CinaColombia;

public class Inventario {
    public static void main(String[] args) {
        int[][] mochila = {
            {3, 0, 1, 0},
            {0, 5, 0, 2},
            {1, 0, 0, 7}
        };

        int vacios = 0, total = 0;

        for (int i = 0; i < mochila.length; i++) {
            for (int j = 0; j < mochila[i].length; j++) {
                if (mochila[i][j] == 0) {
                    vacios++;
                }
                total += mochila[i][j];
            }
        }

        System.out.println("Espacios vacios: " + vacios);
        System.out.println("Total de objetos: " + total);
    }
}