import javax.swing.*;
import java.awt.*;

class GameOfLife {
    private static final int n = 30;
    private static boolean[][] game = new boolean[n][n];
    private static GridDrawer gridDrawer = new GridDrawer(n);

    private static void printGrid() {
        System.out.print("\033[H\033[2J");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (game[i][j])
                    System.out.print('1');
                else
                    System.out.print('.');
            }
            System.out.println();
        }

        System.out.flush();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    } 

    private static boolean isValidCoord(int i, int j) {
        return i >= 0 && j >= 0 && i < n && j < n;
    }

    private static void nextGen() {
        boolean[][] newBoard = new boolean[n][n];
        int[] dirj = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] diri = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                for (int k = 0; k < 8; k++) {
                    int new_i = i + diri[k];
                    int new_j = j + dirj[k];
                    if (isValidCoord(new_i, new_j) && game[new_i][new_j]) count++;
                }
                
                // Conway's Rules
                if (game[i][j] && count < 2) newBoard[i][j] = false;
                else if (game[i][j] && count > 3) newBoard[i][j] = false;
                else if (!game[i][j] && count == 3) newBoard[i][j] = true;
                else if (game[i][j]) newBoard[i][j] = true;

                if (newBoard[i][j]) gridDrawer.fillCell(i, j);
                else gridDrawer.removeCell(i, j);
            }
        }
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                game[i][j] = newBoard[i][j];
        newBoard = null;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game of Life");
        JButton startSim = new JButton();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        startSim.setSize(100, 100);
        startSim.setVisible(true);
        startSim.setText("Start Simulation");

        frame.setLayout(new BoxLayout());
        frame.add(gridDrawer);
        frame.add(startSim);
        frame.setVisible(true);        
        
        game[9][9] = true;
        game[9][10] = true;
        game[9][11] = true;
        gridDrawer.fillCell(9, 10);
        gridDrawer.fillCell(9, 11);
        gridDrawer.fillCell(9, 9);
        gridDrawer.repaint();

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            nextGen();
            gridDrawer.repaint();
        }
    }
};
