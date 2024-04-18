public class main {
 
    public static void imprimirMatriz(String nombre, boolean[][] matriz) {
        System.out.println("Matriz " + nombre + ":");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void Main(String[] args) {
        boolean [][]matriz1={
            {false, true, false, true, false},
            {true, false, true, false, false},
            {false, true, false, true, false},
            {true, false, true, false, true},
            {false, false, false, true, false}
        };
        imprimirMatriz("1", matriz1);

        boolean [][]matriz2={
            {false, true, false, false},
            {true, false, true, false},
            {false, true, false, true},
            {false,false, true, true},
        };
        imprimirMatriz("2", matriz2);

        boolean [][]matriz3={
            {false, true, false, false, true},
            {true, false, true, true, false},
            {false, true, false, false, true},
            {false, true, false, false, true},
            {true, false, true, true, false}
        };
        imprimirMatriz("3", matriz3);

        boolean [][]matriz4={
            {false, true, false, false, false},
            {true, false, true, false, true},
            {false, true, false, true, false},
            {false, false, true, false, false},
            {false, true, false, false, false}
        };
        imprimirMatriz("4", matriz4);

        boolean [][]matriz5={
            {false, true, true, true},
            {true, false, true, false},
            {true, true, false, true},
            {true,false, true, true}
        };
        imprimirMatriz("5", matriz5);
    }
}