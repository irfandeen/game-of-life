import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameOfLife {
    private static final int n = 30;
    private static boolean[][] game = new boolean[n][n];
    private static GridDrawer gridDrawer = new GridDrawer(n);
    private static boolean simStatus = false;

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
        gridDrawer.repaint();
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms); // Pause for 2000ms (2 seconds)
        } catch (InterruptedException ex) {
            System.err.println("Thread was interrupted: " + ex.getMessage());
            Thread.currentThread().interrupt(); // Restore the thread's interrupted status
        }
    }

    private static void simulate() {
        delay(1500);
        nextGen();
        gridDrawer.repaint();
    }

    private static void initState() {
        for(int i = 9; i <= 11; i++) {
            game[10][i] = true;
            gridDrawer.fillCell(10, i);
        }
    }

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);

        initState();

        // Create a button
        JButton startButton = new JButton("Start Simulation");
        JButton resetButton = new JButton("Reset Grid");
        JButton stopButton = new JButton("Stop Simulation");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simStatus = true;
                System.out.println(simStatus);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simStatus = false;
                initState();
                gridDrawer.repaint();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simStatus = false;
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(stopButton);

        // Create a container panel with BorderLayout
        JPanel container = new JPanel(new BorderLayout());
        container.add(gridDrawer, BorderLayout.CENTER); // Add grid to the center
        container.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(container);
        frame.setVisible(true);

        while (true) {
            System.out.println(simStatus);
            delay(1000);
            if (simStatus) {
                nextGen();
                gridDrawer.repaint();
            }
        }
    }
};
