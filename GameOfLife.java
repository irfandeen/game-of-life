class GameOfLife {
    private static final int n = 20;
    private static boolean[][] game = new boolean[n][n];

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
            }
        }
        
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                game[i][j] = newBoard[i][j];
        newBoard = null;
    }

    public static void main(String[] args) {
        // Initial conditions
        game[9][10] = true;
        game[10][11] = true;
        for (int i = 9; i <= 11; i++)
            game[11][i] = true;

        // Simulate
        final int simCount = 10;
        System.out.print("\033[2J");
        for (int i = 0; i < simCount; i++) {
            printGrid();
            nextGen();
        }
    }
};
