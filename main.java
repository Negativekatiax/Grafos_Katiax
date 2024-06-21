import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Main {

    private static int[][] grafo;
    private static int[] distancias;
    private static boolean[] visitados;
    private static int[] previos;
    private static JLabel distanciaLabel;
    private static JLabel rutaLabel;
    private static JTextField inicioField;
    private static JTextField finField;
    private static JFrame frame;
    private static boolean highlightPath;

    public static void dijkstra(int[][] grafo, int inicio, int fin) {
        int n = grafo.length;

        distancias = new int[n];
        Arrays.fill(distancias, Integer.MAX_VALUE);
        distancias[inicio] = 0;

        visitados = new boolean[n];
        previos = new int[n];
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

            // Repaint the graph to visualize the current state
            SwingUtilities.invokeLater(Main::repaintGraph);
            try {
                Thread.sleep(1000); // Pause to see the visualization
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        StringBuilder ruta = new StringBuilder();
        int v = fin;
        while (v != -1) {
            ruta.insert(0, (char) ('A' + v));
            v = previos[v];
            if (v != -1) ruta.insert(0, " -> ");
        }

        highlightPath = true;
        SwingUtilities.invokeLater(Main::repaintGraph);

        distanciaLabel.setText("Distancia mínima: " + distancias[fin]);
        rutaLabel.setText("Ruta más corta: " + ruta);
    }

    public static void main(String[] args) {
        grafo = new int[][]{
                {0, 1, 0, 2, 0},
                {1, 0, 3, 0, 0},
                {0, 3, 0, 4, 0},
                {2, 0, 4, 0, 5},
                {0, 0, 0, 5, 0}
        };

        frame = new JFrame("Visualización de Dijkstra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        GraphPanel graphPanel = new GraphPanel();
        frame.setLayout(new BorderLayout());
        frame.add(graphPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        distanciaLabel = new JLabel("Distancia mínima: ");
        rutaLabel = new JLabel("Ruta más corta: ");
        infoPanel.add(distanciaLabel);
        infoPanel.add(rutaLabel);

        frame.add(infoPanel, BorderLayout.SOUTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Ingrese el vértice de inicio (A, B, C, D, E): "));
        inicioField = new JTextField(2);
        inputPanel.add(inicioField);
        inputPanel.add(new JLabel("Ingrese el vértice final (A, B, C, D, E): "));
        finField = new JTextField(2);
        inputPanel.add(finField);
        JButton startButton = new JButton("Iniciar");
        inputPanel.add(startButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char inicioChar = inicioField.getText().charAt(0);
                int inicio = inicioChar - 'A';
                char finChar = finField.getText().charAt(0);
                int fin = finChar - 'A';
                highlightPath = false;
                new Thread(() -> dijkstra(grafo, inicio, fin)).start();
            }
        });
    }

    private static void repaintGraph() {
        frame.repaint();
    }

    static class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int[][] positions = {
                    {100, 100}, {300, 100}, {500, 100}, {200, 300}, {400, 300}
            };

            for (int i = 0; i < grafo.length; i++) {
                for (int j = 0; j < grafo[i].length; j++) {
                    if (grafo[i][j] != 0) {
                        g2d.drawLine(positions[i][0], positions[i][1], positions[j][0], positions[j][1]);
                        g2d.setColor(Color.BLUE);
                        g2d.drawString(String.valueOf(grafo[i][j]),
                            (positions[i][0] + positions[j][0]) / 2,
                            (positions[i][1] + positions[j][1]) / 2);
                    }
                }
            }

            for (int i = 0; i < grafo.length; i++) {
                if (highlightPath && isInShortestPath(i)) {
                    g2d.setColor(Color.GREEN);
                } else if (visitados != null && visitados[i]) {
                    g2d.setColor(Color.BLUE);
                } else {
                    g2d.setColor(Color.RED);
                }
                g2d.fillOval(positions[i][0] - 15, positions[i][1] - 15, 30, 30);
                g2d.setColor(Color.BLACK);
                g2d.drawString(String.valueOf((char) ('A' + i)), positions[i][0] - 5, positions[i][1] + 5);
            }
        }

        private boolean isInShortestPath(int node) {
            int fin = finField.getText().charAt(0) - 'A';
            while (fin != -1) {
                if (fin == node) {
                    return true;
                }
                fin = previos[fin];
            }
            return false;
        }
    }
}