package CinaColombia;

public class TableroVacio {
    public static void main(String[] args) {
        char[][] t = new char[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                t[i][j] = '#';
            }
        }

        System.out.print("  ");
        for (int j = 0; j < 5; j++) {
            System.out.print(j + " ");
        }
        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 5; j++) {
                System.out.print(t[i][j] + " ");
            }
            System.out.println();
        }
    }
}