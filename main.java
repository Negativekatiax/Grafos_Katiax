import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void dijkstra(int[][] grafo, int inicio, int fin) {
        int n = grafo.length;

        int[] distancias = new int[n];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[inicio] = 0;

        boolean[] visitados = new boolean[n];
        int[] previos = new int[n];
        Arrays.fill(previos, -1);

        for (int i = 0; i < n - 1; i++) {
            int minDistancia = Integer.MAX_VALUE;
            int verticeMin = -1;
            for (int v = 0; v < n; v++) {
                if (!visitados[v] && distancias[v] < minDistancia) {
                    minDistancia = distancias[v];
                    verticeMin = v;
                }
            }

            if (verticeMin == -1) break;

            visitados[verticeMin] = true;

            for (int vecino = 0; vecino < n; vecino++) {
                if (!visitados[vecino] && grafo[verticeMin][vecino] != 0) {
                    int distanciaAcumulada = distancias[verticeMin] + grafo[verticeMin][vecino];
                    if (distanciaAcumulada < distancias[vecino]) {
                        distancias[vecino] = distanciaAcumulada;
                        previos[vecino] = verticeMin;
                    }
                }
            }
        }

        System.out.println("Ruta más corta desde " + (char) ('A' + inicio) + " a " + (char) ('A' + fin) + ":");
        int v = fin;
        StringBuilder ruta = new StringBuilder();
        while (v != -1) {
            ruta.insert(0, (char) ('A' + v));
            v = previos[v];
            if (v != -1) ruta.insert(0, " -> ");
        }
        System.out.println(ruta);
        System.out.println("Distancia mínima: " + distancias[fin]);
    }

    public static void main(String[] args) {
        int[][] grafo = {
                {0, 1, 0, 2, 0},
                {1, 0, 3, 0, 0},
                {0, 3, 0, 4, 0},
                {2, 0, 4, 0, 5},
                {0, 0, 0, 5, 0}
        };

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el vértice de inicio (A, B, C, D, E): ");
        char inicioChar = scanner.next().charAt(0);
        int inicio = inicioChar - 'A';

        System.out.print("Ingrese el vértice final (A, B, C, D, E): ");
        char finChar = scanner.next().charAt(0);
        int fin = finChar - 'A';

        dijkstra(grafo, inicio, fin);
    }
}